package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.KakaoPayApproveRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.CommentDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.FeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response.KakaoPayReadyResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 피드 관련 주요 기능을 조합한 퍼사드 인터페이스입니다.
 * 피드 업로드, 결제 연동, 좋아요/댓글, 조회수 기록 등 다양한 사용자 행위를 담당합니다.
 */
public interface FeedFacade {

	/**
	 * 검색 조건을 기반으로 피드 목록을 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param feedSearchFilter 검색 조건 (지역, 날짜 등)
	 * @return 피드 리스트 응답
	 */
	List<FeedListResponse> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter);

	/**
	 * 일반 사용자가 피드를 업로드합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param content 피드 내용
	 * @param lat 위도
	 * @param lng 경도
	 * @param fileList 첨부 이미지/영상 리스트
	 */
	void uploadFeed(long loginMemberId, String content, Double lat, Double lng, List<MultipartFile> fileList);

	/**
	 * 기업 회원이 유료 피드를 업로드합니다.
	 * 카카오페이 결제 준비 요청이 함께 수행됩니다.
	 *
	 * @param loginMemberId 기업 회원 ID
	 * @param content 피드 내용
	 * @param lat 위도
	 * @param lng 경도
	 * @param fileList 첨부 이미지/영상 리스트
	 * @param payAmount 결제 금액
	 * @return 카카오페이 결제 준비 응답 (QR URL 포함)
	 */
	KakaoPayReadyResponse uploadFeedByCompany(
		long loginMemberId,
		String content,
		Double lat, Double lng,
		List<MultipartFile> fileList,
		int payAmount);

	/**
	 * 사용자가 피드를 삭제합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 삭제할 피드 ID
	 */
	void deleteFeed(long loginMemberId, long feedId);

	/**
	 * 피드 내용을 수정합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 수정 대상 피드 ID
	 * @param newContent 수정된 내용
	 * @param newLat 수정된 위도
	 * @param newLng 수정된 경도
	 * @param newfileList 수정된 첨부 파일 목록
	 */
	void updateFeed(long loginMemberId, long feedId, String newContent, Double newLat, Double newLng,
		List<MultipartFile> newfileList);

	/**
	 * 피드에 좋아요를 추가합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 피드 ID
	 */
	void likeFeed(long loginMemberId, long feedId);

	/**
	 * 피드에 대한 좋아요를 취소합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 피드 ID
	 */
	void unlikeFeed(long loginMemberId, long feedId);

	/**
	 * 피드에 댓글을 등록합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 대상 피드 ID
	 * @param comment 댓글 내용
	 */
	void registerComment(long loginMemberId, long feedId, String comment);

	/**
	 * 피드의 댓글을 수정합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 대상 피드 ID
	 * @param commentId 수정할 댓글 ID
	 * @param newComment 새로운 댓글 내용
	 */
	void updateComment(long loginMemberId, long feedId, long commentId, String newComment);

	/**
	 * 피드의 댓글을 삭제합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 대상 피드 ID
	 * @param commentId 삭제할 댓글 ID
	 */
	void deleteCommit(long loginMemberId, long feedId, long commentId);

	/**
	 * 피드의 댓글 목록을 조회합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param feedId 대상 피드 ID
	 * @return 댓글 응답 리스트
	 */
	List<CommentDetailResponse> searchCommentList(long loginMemberId, long feedId);

	/**
	 * 카카오페이 결제 완료 후, 피드 결제 승인 처리를 수행합니다.
	 *
	 * @param kakaoPayApproveRequest 카카오페이 승인 요청 DTO
	 */
	void approveFeedPay(KakaoPayApproveRequest kakaoPayApproveRequest);

	/**
	 * 피드 조회 로그를 사용자 ID 기준으로 기록합니다. (중복 방지용 Redis 사용)
	 *
	 * @param memberId 사용자 ID
	 * @param feedId 피드 ID
	 */
	void addViewLog(long memberId, long feedId);

	/**
	 * 피드 조회 로그를 IP 주소 기준으로 기록합니다. (비회원 조회 추적용)
	 *
	 * @param ip 클라이언트 IP 주소
	 * @param feedId 피드 ID
	 */
	void addViewLogByIp(String ip, long feedId);
}