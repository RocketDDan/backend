package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

/**
 * 피드에 업로드된 파일 경로를 관리하는 서비스 인터페이스입니다.
 * 파일 경로 저장, 조회, 전체 삭제 기능을 제공합니다.
 */
public interface FeedFileService {

	/**
	 * 피드에 업로드된 파일 경로들을 저장합니다.
	 *
	 * @param loginMemberId 파일 업로드 요청자 ID
	 * @param feedId 대상 피드 ID
	 * @param uploadedfilePathList 업로드된 파일의 S3 경로 리스트
	 */
	void save(long loginMemberId, long feedId, List<String> uploadedfilePathList);

	/**
	 * 특정 피드에 연결된 파일 경로 리스트를 조회합니다.
	 *
	 * @param feedId 대상 피드 ID
	 * @return 파일 경로 리스트 (예: S3 URL)
	 */
	List<String> searchFilePathList(long feedId);

	/**
	 * 피드에 연결된 모든 파일 경로를 삭제합니다.
	 * (DB 상의 메타데이터만 삭제하며, 실제 S3 객체 삭제는 별도 처리 필요)
	 *
	 * @param loginMemberId 삭제 요청자 ID
	 * @param feedId 대상 피드 ID
	 */
	void deleteAll(long loginMemberId, long feedId);
}