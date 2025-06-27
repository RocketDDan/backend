package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 공지사항 관련 기능을 담당하는 퍼사드 인터페이스.
 * 게시글 등록, 수정, 삭제, 조회 등의 고수준 비즈니스 로직을 제공합니다.
 */
public interface AnnouncementFacade {

	/**
	 * 공지사항을 수정합니다.
	 *
	 * @param announcementId 수정할 공지사항 ID
	 * @param request        수정 요청 데이터 (제목, 내용 등)
	 * @param memberId       요청한 사용자 ID
	 * @param role           사용자 역할 (ADMIN, COMPANY 등)
	 */
	void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest request, Long memberId, String role);

	/**
	 * 공지사항을 삭제합니다.
	 *
	 * @param announcementId 삭제할 공지사항 ID
	 * @param memberId       요청한 사용자 ID
	 * @param role           사용자 역할 (ADMIN, COMPANY 등)
	 */
	void deleteAnnouncement(Long announcementId, Long memberId, String role);

	/**
	 * 공지사항 목록을 조회합니다.
	 *
	 * @param params   검색 및 페이징 파라미터 (keyword, offset, perPage 등)
	 * @param memberId 요청한 사용자 ID
	 * @param role     사용자 역할 (ADMIN, COMPANY 등)
	 * @return 공지사항 목록 응답 리스트
	 */
	List<AnnouncementListResponse> getAnnouncementList(Map<String, Object> params, Long memberId, String role);

	/**
	 * 공지사항 상세 정보를 조회합니다.
	 *
	 * @param announcementId 조회할 공지사항 ID
	 * @return 공지사항 상세 응답
	 */
	AnnouncementDetailResponse getAnnouncementDetail(Long announcementId);

	/**
	 * 공지사항 전체 수를 조회합니다.
	 *
	 * @param params   검색 조건 파라미터
	 * @param memberId 요청한 사용자 ID
	 * @param role     사용자 역할 (ADMIN, COMPANY 등)
	 * @return 전체 공지사항 수
	 */
	int getAnnouncementTotalCount(Map<String, Object> params, Long memberId, String role);

	/**
	 * 새로운 공지사항을 등록합니다.
	 *
	 * @param title    공지 제목
	 * @param content  공지 내용
	 * @param files    첨부 파일 리스트 (이미지, PDF 등)
	 * @param memberId 작성자 ID
	 * @param role     작성자 역할 (ADMIN, COMPANY 등)
	 */
	void createAnnouncement(String title, String content, List<MultipartFile> files, Long memberId, String role);
}