package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.announcement;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.AnnouncementFileCreate;

@Mapper
public interface AnnouncementFileMapper {

	void insertAnnouncementFile(AnnouncementFileCreate file);

	void updateAnnouncementFile(AnnouncementFileCreate file);

	boolean existsByAnnouncementId(Long announcementId);

	void deleteByAnnouncementId(Long announcementId);

	List<String> findFilePathsByAnnouncementId(Long announcementId);

	void deleteFileByPath(Long announcementId, String filePath);

}
