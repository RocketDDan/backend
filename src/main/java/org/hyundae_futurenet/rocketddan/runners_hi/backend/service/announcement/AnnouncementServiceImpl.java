package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;
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

	@Override
	public List<AnnouncementListResponse> findAnnouncements(Map<String, Object> params) {

		return announcementMapper.findAnnouncements(params);
	}

	@Override
	public AnnouncementDetailResponse findDetailById(Long announcementId) {

		return announcementMapper.findDetailById(announcementId);
	}
}
