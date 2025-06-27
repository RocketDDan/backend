package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.AnnouncementFileCreate;

/**
 * 공지사항(Announcement)의 첨부파일을 관리하는 서비스 인터페이스입니다.
 * 파일 경로 저장, 조회, 개별 또는 전체 삭제 기능을 제공합니다.
 */
public interface AnnouncementFileService {

	/**
	 * 공지사항 첨부파일 경로를 저장합니다.
	 *
	 * @param file 저장할 첨부파일 정보 (공지 ID, 경로 등 포함)
	 */
	void insertFile(AnnouncementFileCreate file);

	/**
	 * 특정 공지사항에 연결된 모든 첨부파일을 삭제합니다.
	 *
	 * @param announcementId 대상 공지사항 ID
	 */
	void deleteFilesByAnnouncementId(Long announcementId);

	/**
	 * 특정 공지사항에 연결된 첨부파일 경로 리스트를 조회합니다.
	 *
	 * @param announcementId 대상 공지사항 ID
	 * @return 첨부파일 경로 리스트 (예: S3 URL)
	 */
	List<String> findFilePathsByAnnouncementId(Long announcementId);

	/**
	 * 특정 공지사항의 개별 첨부파일을 파일 경로 기준으로 삭제합니다.
	 *
	 * @param announcementId 대상 공지사항 ID
	 * @param filePath 삭제할 파일 경로
	 */
	void deleteFileByPath(Long announcementId, String filePath);
}