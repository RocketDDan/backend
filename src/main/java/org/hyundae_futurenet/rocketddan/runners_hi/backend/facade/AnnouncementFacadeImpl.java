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

		if ("USER".equals(role)) {
			// 크루 멤버의 리더임을 확인
			boolean isLeader = crewMemberMapper.existsLeaderByMemberId(memberId);
			if (!isLeader) {
				throw new IllegalStateException("공지사항 등록 권한이 없습니다.");
			}
		}

		String type = ("ADMIN".equals(role) || "COMPANY".equals(role)) ? "ALL" : "CREW";

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
}
