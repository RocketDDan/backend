package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness.AnnouncementFileCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.announcement.AnnouncementFileMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementFileServiceImpl implements AnnouncementFileService {

	private final AnnouncementFileMapper announcementFileMapper;

	@Override
	public void insertFile(AnnouncementFileCreate file) {

		announcementFileMapper.insertAnnouncementFile(file);
	}
}
