package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.announcement;

import org.apache.ibatis.annotations.Mapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;

@Mapper
public interface AnnouncementMapper {

	void insertAnnouncement(AnnouncementCreate announcementCreate);
}
