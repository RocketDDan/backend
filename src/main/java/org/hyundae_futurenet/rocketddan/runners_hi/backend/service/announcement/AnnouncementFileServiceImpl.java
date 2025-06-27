package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.AnnouncementFileCreate;
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

	@Override
	public void deleteFilesByAnnouncementId(Long announcementId) {

		announcementFileMapper.deleteByAnnouncementId(announcementId);
	}

	@Override
	public List<String> findFilePathsByAnnouncementId(Long announcementId) {

		return announcementFileMapper.findFilePathsByAnnouncementId(announcementId);
	}

	@Override
	public void deleteFileByPath(Long announcementId, String filePath) {

		announcementFileMapper.deleteFileByPath(announcementId, filePath);
	}
}
