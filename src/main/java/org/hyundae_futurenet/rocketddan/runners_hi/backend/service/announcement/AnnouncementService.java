package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.announcement;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.AnnouncementCreate;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;

/**
 * 공지사항(Announcement) 관련 CRUD 및 조회 기능을 제공하는 서비스 인터페이스입니다.
 */
public interface AnnouncementService {

	/**
	 * 새로운 공지사항을 등록합니다.
	 *
	 * @param announcementCreate 공지 등록 요청 데이터 (제목, 내용, 파일 등 포함)
	 */
	void insertAnnouncement(AnnouncementCreate announcementCreate);

	/**
	 * 기존 공지사항을 수정합니다.
	 *
	 * @param announcementCreate 수정할 공지사항 데이터
	 */
	void updateAnnouncement(AnnouncementCreate announcementCreate);

	/**
	 * 공지사항 ID로 공지 데이터를 조회합니다.
	 * (수정 시 활용용 - 파일 포함)
	 *
	 * @param announcementId 조회할 공지사항 ID
	 * @return 공지 작성 요청 DTO (수정용)
	 */
	AnnouncementCreate findById(Long announcementId);

	/**
	 * 특정 공지사항을 삭제합니다.
	 *
	 * @param announcementId 삭제할 공지사항 ID
	 */
	void deleteAnnouncement(Long announcementId);

	/**
	 * 공지사항 목록을 조회합니다. (검색, 페이징 가능)
	 *
	 * @param params 검색 및 페이징 파라미터 (예: keyword, offset, perPage 등)
	 * @return 공지사항 리스트 응답
	 */
	List<AnnouncementListResponse> findAnnouncements(Map<String, Object> params);

	/**
	 * 공지사항 상세 정보를 조회합니다.
	 *
	 * @param announcementId 조회할 공지사항 ID
	 * @return 공지 상세 응답
	 */
	AnnouncementDetailResponse findDetailById(Long announcementId);

	/**
	 * 조건에 맞는 공지사항 전체 수를 반환합니다.
	 *
	 * @param params 검색 조건 (예: keyword 등)
	 * @return 공지사항 개수
	 */
	int countAnnouncements(Map<String, Object> params);
}