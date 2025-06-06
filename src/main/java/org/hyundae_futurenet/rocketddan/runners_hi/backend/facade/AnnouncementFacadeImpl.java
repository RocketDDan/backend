package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementFileCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementCreateRequest;
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

		if ("COMPANY".equals(role)) {
			throw new IllegalStateException("공지사항 등록 권한이 없습니다.");
		}

		if ("USER".equals(role)) {
			// 크루 멤버의 리더임을 확인
			boolean isLeader = crewMemberMapper.existsLeaderByMemberId(memberId);
			if (!isLeader) {
				throw new IllegalStateException("공지사항 등록 권한이 없습니다.");
			}
		}

		String type = "ADMIN".equals(role) ? "ALL" : "CREW";

		AnnouncementCreate create = new AnnouncementCreate(
			null,
			type,
			request.getTitle(),
			request.getContent(),
			memberId
		);

		announcementService.insertAnnouncement(create);

		// 첨부 파일이 있을 경우에만
		if (request.getAttachPath() != null && !request.getAttachPath().isBlank()) {
			AnnouncementFileCreate fileCreate = new AnnouncementFileCreate(
				null,
				create.getAnnouncementId(),
				request.getAttachPath(),
				memberId
			);
			announcementFileService.insertFile(fileCreate);
		}

	}

	@Override
	public void updateAnnouncement(Long announcementId, AnnouncementCreateRequest request, Long memberId, String role) {

		AnnouncementCreate announcement = announcementService.findById(announcementId);

		if (announcement == null) {
			throw new IllegalArgumentException("해당 공지사항이 없습니다.");
		}
		if ("ADMIN".equals(role)) {
			// 전체 수정
		} else if ("USER".equals(role)) {
			boolean isLeader = crewMemberMapper.existsLeaderByMemberId(memberId);
			if (!isLeader || !announcement.getCreatedBy().equals(memberId)) {
				throw new IllegalStateException("공지사항 수정 권한이 없습니다.");
			}
		} else {
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
		if (request.getAttachPath() != null) {
			announcementFileService.updateOrInsertFile(announcementId, request.getAttachPath(), memberId);
		}
	}
}
