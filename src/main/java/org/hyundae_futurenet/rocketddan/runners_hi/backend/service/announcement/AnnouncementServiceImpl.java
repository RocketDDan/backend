package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.announcement.AnnouncementMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

	private final AnnouncementMapper announcementMapper;

	@Override
	public void insertAnnouncement(AnnouncementCreate announcementCreate) {

		announcementMapper.insertAnnouncement(announcementCreate);
	}

	@Override
	public void updateAnnouncement(AnnouncementCreate announcementCreate) {

		announcementMapper.updateAnnouncement(announcementCreate);
	}

	@Override
	public AnnouncementCreate findById(Long announcementId) {

		return announcementMapper.findById(announcementId);
	}

	@Override
	public void deleteAnnouncement(Long announcementId) {

		announcementMapper.deleteAnnouncement(announcementId);
	}
}
