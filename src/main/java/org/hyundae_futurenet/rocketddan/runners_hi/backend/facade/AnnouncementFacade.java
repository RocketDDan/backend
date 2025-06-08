package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;

public interface AnnouncementFacade {

	void createAnnouncement(AnnouncementCreateRequest request, Long memberId, String role);

	void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest request, Long memberId, String role);

	void deleteAnnouncement(Long announcementId, Long memberId, String role);

	List<AnnouncementListResponse> getAnnouncementList(Map<String, Object> params, Long memberId, String role);

	AnnouncementDetailResponse getAnnouncementDetail(Long announcementId);
}