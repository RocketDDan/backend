package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;
import java.util.NoSuchElementException;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CommentDetailSource;

/**
 * 피드 댓글(Comment) 관련 기능을 담당하는 서비스 인터페이스입니다.
 * 댓글 등록, 수정, 삭제, 조회 및 소유자 검증 기능을 제공합니다.
 */
public interface FeedCommentService {

	/**
	 * 특정 피드에 등록된 모든 댓글을 삭제합니다.
	 * 일반적으로 피드 삭제 시 함께 사용됩니다.
	 *
	 * @param feedId 대상 피드 ID
	 */
	void deleteAll(long feedId);

	/**
	 * 피드에 댓글을 등록합니다.
	 *
	 * @param loginMemberId 댓글 작성자 ID
	 * @param feedId 대상 피드 ID
	 * @param comment 댓글 내용
	 */
	void register(long loginMemberId, long feedId, String comment);

	/**
	 * 특정 댓글이 피드에 존재하고, 요청자가 작성자인지 검증합니다.
	 * 수정 또는 삭제 전 권한 확인용으로 사용됩니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param feedId 피드 ID
	 * @param commentId 댓글 ID
	 * @throws NoSuchElementException 또는 권한 관련 예외
	 */
	void assertCommentExists(long loginMemberId, long feedId, long commentId);

	/**
	 * 댓글 내용을 수정합니다.
	 *
	 * @param commentId 댓글 ID
	 * @param newComment 새로운 댓글 내용
	 */
	void update(long commentId, String newComment);

	/**
	 * 댓글을 삭제합니다.
	 *
	 * @param commentId 삭제할 댓글 ID
	 */
	void delete(long commentId);

	/**
	 * 특정 피드에 대한 댓글 목록을 조회합니다.
	 *
	 * @param loginMemberId 요청자 ID (권한 검증 또는 마스킹 용도)
	 * @param feedId 피드 ID
	 * @return 댓글 상세 정보 리스트
	 */
	List<CommentDetailSource> searchCommentList(long loginMemberId, long feedId);
}