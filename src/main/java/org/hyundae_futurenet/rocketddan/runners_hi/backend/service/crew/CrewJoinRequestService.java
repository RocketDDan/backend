package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CrewJoinRequestSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;

/**
 * 크루 가입 요청 관련 로직을 담당하는 서비스 인터페이스입니다.
 * 가입 요청 등록, 상태 변경, 삭제 및 다양한 조회 기능을 제공합니다.
 */
public interface CrewJoinRequestService {

	/**
	 * 크루 가입 요청을 등록합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param crewId 대상 크루 ID
	 * @param crewJoinRequest 가입 요청 내용 (메시지 등)
	 */
	void insertCrewJoinRequest(long loginMemberId, long crewId, CrewJoinRequest crewJoinRequest);

	/**
	 * 크루 가입 요청의 상태를 변경합니다. (수락, 거절 등)
	 *
	 * @param loginMemberId 처리자 ID (크루장)
	 * @param crewJoinRequestId 대상 가입 요청 ID
	 * @param status 변경할 상태
	 */
	void updateCrewJoinRequest(long loginMemberId, long crewJoinRequestId, CrewJoinRequestStatus status);

	/**
	 * 크루 가입 요청 목록을 상태 필터 기준으로 조회합니다.
	 *
	 * @param crewId 대상 크루 ID
	 * @param filter 상태, 닉네임 등 다양한 검색 조건
	 * @return 가입 요청 응답 리스트
	 */
	List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(long crewId, CrewJoinRequestSearchFilter filter);

	/**
	 * 특정 조건에 해당하는 가입 요청의 전체 개수를 조회합니다.
	 *
	 * @param crewId 대상 크루 ID
	 * @param status 요청 상태 필터
	 * @param nickname 닉네임 검색 키워드
	 * @return 가입 요청 총 개수
	 */
	int totalCount(long crewId, String status, String nickname);

	/**
	 * 로그인 사용자의 가입 요청을 삭제합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param crewId 대상 크루 ID
	 */
	void deleteCrewJoinRequest(long loginMemberId, long crewId);

	/**
	 * 특정 가입 요청의 신청자 이메일을 조회합니다.
	 *
	 * @param crewJoinRequestId 가입 요청 ID
	 * @return 신청자 이메일 주소
	 */
	String selectEmailByCrewJoinRequestId(long crewJoinRequestId);

	/**
	 * 로그인 사용자의 가입 요청 정보를 조회합니다.
	 *
	 * @param crewMemberId 사용자 ID
	 * @return 가입 요청 정보 (크루, 상태, 메시지 등 포함)
	 */
	CrewJoinRequestSource selectCrewJoinRequestByMemberId(long crewMemberId);
}