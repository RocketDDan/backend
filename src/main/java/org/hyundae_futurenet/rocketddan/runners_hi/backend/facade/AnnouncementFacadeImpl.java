package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementFileCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMemberMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement.AnnouncementFileService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement.AnnouncementService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementFacadeImpl implements AnnouncementFacade {

	private final AnnouncementService announcementService;

	private final AnnouncementFileService announcementFileService;

	private final CrewMemberMapper crewMemberMapper;

	@Override
	public void createAnnouncement(AnnouncementCreateRequest request, Long memberId, String role) {
		// COMPANY는 공지사항 등록 할 수 없음
		if ("COMPANY".equals(role)) {
			throw new IllegalStateException("공지사항 등록 권한이 없습니다.");
		}
		// USER일 경우 크루장일 경우에만 공지사항 등록 가능
		if ("USER".equals(role)) {
			// 크루 멤버의 리더임을 확인
			boolean isLeader = crewMemberMapper.existsLeaderByMemberId(memberId);
			if (!isLeader) {
				throw new IllegalStateException("공지사항 등록 권한이 없습니다.");
			}
		}
		// ADMIN일 경우에는 ALL 타입으로 저장
		String type = "ADMIN".equals(role) ? "ALL" : "CREW";

		AnnouncementCreate create = new AnnouncementCreate(
			null,
			type,
			request.getTitle(),
			request.getContent(),
			memberId
		);

		announcementService.insertAnnouncement(create);

		// 첨부 파일이 존재할 경우에만 저장
		List<String> attachPaths = request.getAttachPaths();
		if (attachPaths != null && !attachPaths.isEmpty()) {
			// 최대 3개까지만 저장 가능
			if (attachPaths.size() > 3) {
				throw new IllegalArgumentException("첨부파일은 최대 3개까지 가능합니다.");
			}
			// 이미지(jpg, png), pdf 파일만 저장 가능
			for (String filePath : attachPaths) {
				if (!isValidFileType(filePath)) {
					throw new IllegalArgumentException("첨부파일은 jpg, png, pdf 형식만 허용됩니다.");
				}

				AnnouncementFileCreate fileCreate = new AnnouncementFileCreate(
					null,
					create.getAnnouncementId(),
					filePath,
					memberId
				);
				announcementFileService.insertFile(fileCreate);
			}
		}

	}

	@Override
	public void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest request, Long memberId, String role) {
		// 공지사항 존재 여부 유효성 검사
		AnnouncementCreate announcement = announcementService.findById(announcementId);
		if (announcement == null) {
			throw new IllegalArgumentException("해당 공지사항이 없습니다.");
		}
		// ADMIN일 경우 전체 수정 가능
		if ("ADMIN".equals(role)) {
		} else if ("USER".equals(role)) {
			// USER일 경우 크루장이 아니라면 수정 금지
			boolean isLeader = crewMemberMapper.existsLeaderByMemberId(memberId);
			if (!isLeader || !announcement.getCreatedBy().equals(memberId)) {
				throw new IllegalStateException("공지사항 수정 권한이 없습니다.");
			}
		} else {
			// 그 외에도 수정 금지
			throw new IllegalStateException("공지사항 수정 권한이 없습니다.");
		}

		AnnouncementCreate updated = new AnnouncementCreate(
			announcementId,
			announcement.getAnnouncementType(),
			request.getTitle(),
			request.getContent(),
			memberId
		);
		announcementService.updateAnnouncement(updated);

		// 첨부파일 수정 처리
		// 클라이언트가 attachPath 보내주지 않으면 그대로 유지 / 그게 아니라면 자유롭게 수정 가능
		if (request.getAttachPaths() != null) {
			List<String> newPaths = request.getAttachPaths();
			if (newPaths.size() > 3) {
				throw new IllegalArgumentException("첨부파일은 최대 3개까지 등록할 수 있습니다.");
			}
			for (String path : newPaths) {
				if (!isValidFileType(path)) {
					throw new IllegalArgumentException("첨부파일은 jpg, png, pdf 형식만 허용됩니다.");
				}
			}

			List<String> existingPaths = announcementFileService.findFilePathsByAnnouncementId(announcementId);
			Set<String> existingSet = new HashSet<>(existingPaths);
			Set<String> newSet = new HashSet<>(newPaths);

			// 삭제된 파일 제거
			existingSet.stream()
				.filter(path -> !newSet.contains(path))
				.forEach(path -> announcementFileService.deleteFileByPath(announcementId, path));

			// 새로 추가된 파일 등록
			newSet.stream()
				.filter(path -> !existingSet.contains(path))
				.forEach(path -> {
					AnnouncementFileCreate fileCreate = new AnnouncementFileCreate(
						null,
						announcementId,
						path,
						memberId
					);
					announcementFileService.insertFile(fileCreate);
				});
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
		// 첨부파일 조회는 따로 처리
		List<String> attachPaths = announcementFileService.findFilePathsByAnnouncementId(announcementId);
		detail.setAttachPaths(attachPaths);
		return detail;
	}

	// 파일 확장자 유효성 검사 - 파일 위치 확인 필요
	private boolean isValidFileType(String filePath) {

		String lower = filePath.toLowerCase();
		return lower.endsWith(".jpg") || lower.endsWith(".png") || lower.endsWith(".pdf");
	}

}
