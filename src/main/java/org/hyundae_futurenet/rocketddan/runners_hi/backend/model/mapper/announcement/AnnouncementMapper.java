package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.announcement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;

@Mapper
public interface AnnouncementMapper {

	void insertAnnouncement(AnnouncementCreate announcementCreate);

	void updateAnnouncement(AnnouncementCreate announcementCreate);

	AnnouncementCreate findById(Long announcementId);

	void deleteAnnouncement(Long announcementId);

	List<AnnouncementListResponse> findAnnouncements(@Param("params") Map<String, Object> params);
}
