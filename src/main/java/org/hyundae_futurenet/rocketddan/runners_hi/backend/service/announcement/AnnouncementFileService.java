package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementFileCreate;

public interface AnnouncementFileService {

	void insertFile(AnnouncementFileCreate file);

	void updateOrInsertFile(Long announcementId, String attachPath, Long memberId);

	void deleteFilesByAnnouncementId(Long announcementId);
}
