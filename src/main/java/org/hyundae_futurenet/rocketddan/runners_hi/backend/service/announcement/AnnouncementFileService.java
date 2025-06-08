package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementFileCreate;

public interface AnnouncementFileService {

	void insertFile(AnnouncementFileCreate file);

	void deleteFilesByAnnouncementId(Long announcementId);

	List<String> findFilePathsByAnnouncementId(Long announcementId);

	void deleteFileByPath(Long announcementId, String filePath);

}
