package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementFileCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMemberMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement.AnnouncementFileService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement.AnnouncementService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.S3FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementFacadeImpl implements AnnouncementFacade {

	private final AnnouncementService announcementService;

	private final AnnouncementFileService announcementFileService;

	private final CrewMemberMapper crewMemberMapper;

	private final S3FileUtil s3FileUtil;

	@Override
	@Transactional
	public void createAnnouncement(String title, String content, List<MultipartFile> files, Long memberId,
		String role) {

		// 권한 체크
		if ("COMPANY".equals(role)) {
			throw new IllegalStateException("공지사항 등록 권한이 없습니다.");
		}
		if ("USER".equals(role) && !crewMemberMapper.existsLeaderByMemberId(memberId)) {
			throw new IllegalStateException("공지사항 등록 권한이 없습니다.");
		}

		// 파일 유효성 검사
		if (files != null) {
			if (files.size() > 3) {
				throw new IllegalArgumentException("첨부파일은 최대 3개까지 가능합니다.");
			}

			for (MultipartFile file : files) {
				String filename = file.getOriginalFilename();
				s3FileUtil.validateAllowedFileType(filename);
			}
		}

		// 공지사항 저장
		String type = "ADMIN".equals(role) ? "ALL" : "CREW";

		AnnouncementCreate create = new AnnouncementCreate(
			null, type, title, content, memberId
		);
		announcementService.insertAnnouncement(create);

		// 파일 업로드 및 파일 테이블 저장
		if (files != null && !files.isEmpty()) {
			List<String> uploadedPaths = s3FileUtil.uploadAnnouncementFile(files, create.getAnnouncementId());

			for (String path : uploadedPaths) {
				AnnouncementFileCreate fileCreate = new AnnouncementFileCreate(
					null,
					create.getAnnouncementId(),
					path,
					memberId
				);
				announcementFileService.insertFile(fileCreate);
			}
		}
	}

	@Override
	@Transactional
	public void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest request, Long memberId, String role) {

		// 공지 존재 여부 확인
		AnnouncementCreate announcement = announcementService.findById(announcementId);
		if (announcement == null) {
			throw new IllegalArgumentException("해당 공지사항이 없습니다.");
		}

		// 권한 체크
		if ("ADMIN".equals(role)) {
		} else if ("USER".equals(role)) {
			boolean isLeader = crewMemberMapper.existsLeaderByMemberId(memberId);
			if (!isLeader || !announcement.getCreatedBy().equals(memberId)) {
				throw new IllegalStateException("공지사항 수정 권한이 없습니다.");
			}
		} else {
			throw new IllegalStateException("공지사항 수정 권한이 없습니다.");
		}

		// 본문 수정
		AnnouncementCreate updated = new AnnouncementCreate(
			announcementId,
			announcement.getAnnouncementType(),
			request.getTitle(),
			request.getContent(),
			memberId
		);
		announcementService.updateAnnouncement(updated);

		// 기존 파일 전부 삭제
		List<String> existingPaths = announcementFileService.findFilePathsByAnnouncementId(announcementId);
		if (!existingPaths.isEmpty()) {
			s3FileUtil.removeFiles(existingPaths); // S3 삭제
			announcementFileService.deleteFilesByAnnouncementId(announcementId); // DB 삭제
		}

		// 새 파일 업로드
		List<MultipartFile> newFiles = request.getNewFiles() != null ? request.getNewFiles() : new ArrayList<>();

		if (newFiles.size() > 3) {
			throw new IllegalArgumentException("첨부파일은 최대 3개까지 등록할 수 있습니다.");
		}

		List<String> uploadedPaths = s3FileUtil.uploadAnnouncementFile(newFiles, announcementId);

		for (String path : uploadedPaths) {
			announcementFileService.insertFile(new AnnouncementFileCreate(
				null,
				announcementId,
				path,
				memberId
			));
		}
	}

	@Override
	public void deleteAnnouncement(Long announcementId, Long memberId, String role) {
		// 공지사항 존재 유효성 검사
		AnnouncementCreate announcement = announcementService.findById(announcementId);
		if (announcement == null) {
			throw new IllegalArgumentException("해당 공지사항이 존재하지 않습니다.");
		}
		// ADMIN일 경우
		if ("ADMIN".equals(role)) {
			// 전체 삭제 가능
		} else if ("USER".equals(role)) {
			// USER이고, 크루장일 경우에만 삭제 가능
			boolean isLeader = crewMemberMapper.existsLeaderByMemberId(memberId);
			if (!isLeader || !announcement.getCreatedBy().equals(memberId)) {
				throw new IllegalStateException("공지사항 삭제 권한이 없습니다.");
			}
		} else {
			throw new IllegalStateException("공지사항 삭제 권한이 없습니다.");
		}

		announcementFileService.deleteFilesByAnnouncementId(announcementId);
		announcementService.deleteAnnouncement(announcementId);
	}

	@Override
	public List<AnnouncementListResponse> getAnnouncementList(Map<String, Object> params, Long memberId, String role) {
		// 공지사항 목록 조회
		if (params == null) {
			params = new HashMap<>();
		}
		// scope가 존재하지 않는다면 role에 따라 조회
		if (!params.containsKey("scope")) {
			// ADMIN이거나 COMPANY일 경우에는 전체 공지사항 조회
			if ("ADMIN".equals(role) || "COMPANY".equals(role)) {
				params.put("scope", "ALL_AND_CREW");
			} else if ("USER".equals(role)) {
				// USER이고, 크루 멤버일 경우
				boolean isCrewMember = crewMemberMapper.isCrewMember(memberId);
				if (isCrewMember) {
					// 본인 크루장 공지와 관리자 공지사항만 조회 가능
					Long leaderId = crewMemberMapper.findCrewLeaderIdByMemberId(memberId);
					params.put("scope", "CREW_OR_ALL");
					params.put("leaderId", leaderId);
				} else {
					// 그게 아니라면 관리자 공지사항만 조회 가능
					params.put("scope", "ALL");
				}
			} else {
				// 그 외에는 전체 공지사항만 조회 가능
				params.put("scope", "ALL");
			}
		}

		return announcementService.findAnnouncements(params);
	}

	@Override
	public AnnouncementDetailResponse getAnnouncementDetail(Long announcementId) {
		// 공지사항 세부 조회
		AnnouncementDetailResponse detail = announcementService.findDetailById(announcementId);
		if (detail == null) {
			throw new IllegalArgumentException("해당 공지사항이 존재하지 않습니다.");
		}
		// 첨부파일 조회는 따로 처리
		List<String> attachPaths = announcementFileService.findFilePathsByAnnouncementId(announcementId);
		detail.setAttachPaths(attachPaths);
		return detail;
	}

	@Override
	public int getAnnouncementTotalCount(Map<String, Object> params, Long memberId, String role) {

		if (params == null) {
			params = new HashMap<>();
		}

		if (!params.containsKey("scope")) {
			if ("ADMIN".equals(role) || "COMPANY".equals(role)) {
				params.put("scope", "ALL_AND_CREW");
			} else if ("USER".equals(role)) {
				boolean isCrewMember = crewMemberMapper.isCrewMember(memberId);
				if (isCrewMember) {
					Long leaderId = crewMemberMapper.findCrewLeaderIdByMemberId(memberId);
					params.put("scope", "CREW_OR_ALL");
					params.put("leaderId", leaderId);
				} else {
					params.put("scope", "ALL");
				}
			} else {
				params.put("scope", "ALL");
			}
		}

		return announcementService.countAnnouncements(params);
	}

	private String extractFileName(String fullPath) {

		if (fullPath == null)
			return "";
		return fullPath.substring(fullPath.lastIndexOf("/") + 1);
	}

}
