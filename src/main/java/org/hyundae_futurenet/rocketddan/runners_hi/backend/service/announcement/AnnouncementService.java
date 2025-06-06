package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;

public interface AnnouncementService {

	void insertAnnouncement(AnnouncementCreate announcementCreate);

	void updateAnnouncement(AnnouncementCreate announcementCreate);

	AnnouncementCreate findById(Long announcementId);

	void deleteAnnouncement(Long announcementId);
}
