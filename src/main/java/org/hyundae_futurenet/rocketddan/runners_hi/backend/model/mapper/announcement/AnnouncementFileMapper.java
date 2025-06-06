package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.announcement;

import org.apache.ibatis.annotations.Mapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementFileCreate;

@Mapper
public interface AnnouncementFileMapper {

	void insertAnnouncementFile(AnnouncementFileCreate file);

	void updateAnnouncementFile(AnnouncementFileCreate file);

	boolean existsByAnnouncementId(Long announcementId);

	void deleteByAnnouncementId(Long announcementId);
}
