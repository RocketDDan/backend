package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;

public interface AnnouncementService {

	void insertAnnouncement(AnnouncementCreate announcementCreate);

	void updateAnnouncement(AnnouncementCreate announcementCreate);

	AnnouncementCreate findById(Long announcementId);

	void deleteAnnouncement(Long announcementId);

	List<AnnouncementListResponse> findAnnouncements(Map<String, Object> params);

	AnnouncementDetailResponse findDetailById(Long announcementId);

	int countAnnouncements(Map<String, Object> params);
}
