package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CrewJoinRequestSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 크루(Crew) 관련 주요 비즈니스 로직을 조합해 제공하는 퍼사드 인터페이스입니다.
 * 크루 생성, 수정, 가입 요청, 멤버 관리 등 다양한 크루 도메인 기능을 제공합니다.
 */
public interface CrewFacade {

	/**
	 * 새로운 크루를 생성합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewCreateRequest 크루 생성 요청 정보
	 * @param file 크루 프로필 이미지
	 * @return 생성된 크루의 ID
	 */
	long insertCrew(long loginMemberId, CrewCreateRequest crewCreateRequest, MultipartFile file);

	/**
	 * 크루 정보를 수정합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 수정할 크루 ID
	 * @param crewUpdateRequest 수정 요청 데이터
	 * @param file 새 프로필 이미지 (선택)
	 */
	void updateCrew(long loginMemberId, long crewId, CrewUpdateRequest crewUpdateRequest, MultipartFile file);

	/**
	 * 크루를 삭제합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 삭제할 크루 ID
	 */
	void deleteCrew(long loginMemberId, long crewId);

	/**
	 * 특정 크루의 상세 정보를 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 조회할 크루 ID
	 * @return 크루 상세 응답
	 */
	CrewDetailResponse selectCrewByCrewId(long loginMemberId, long crewId);

	/**
	 * 검색 필터를 이용하여 크루 목록을 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewSearchFilter 검색 조건
	 * @return 크루 목록 응답 리스트
	 */
	List<CrewListResponse> selectCrewsByFilter(long loginMemberId, CrewSearchFilter crewSearchFilter);

	/**
	 * 특정 크루에 가입 요청을 보냅니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param crewId 요청 대상 크루 ID
	 * @param crewJoinRequest 가입 요청 메시지 등
	 */
	void insertCrewJoinRequest(long loginMemberId, long crewId, CrewJoinRequest crewJoinRequest);

	/**
	 * 크루 가입 요청의 상태를 변경합니다. (수락/거절 등)
	 *
	 * @param loginMemberId 처리자 ID
	 * @param crewId 대상 크루 ID
	 * @param crewJoinRequestId 요청 ID
	 * @param status 변경할 상태
	 */
	void updateCrewJoinRequest(long loginMemberId, long crewId, long crewJoinRequestId, CrewJoinRequestStatus status);

	/**
	 * 크루 가입 요청 목록을 상태에 따라 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 대상 크루 ID
	 * @param crewJoinRequestSearchFilter 검색 조건
	 * @return 가입 요청 응답 리스트
	 */
	List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(
		long loginMemberId,
		long crewId,
		CrewJoinRequestSearchFilter crewJoinRequestSearchFilter);

	/**
	 * 가입 요청 전체 수를 조회합니다.
	 *
	 * @param crewId 대상 크루 ID
	 * @param status 상태 필터
	 * @param nickname 닉네임 검색 필터
	 * @return 총 요청 수
	 */
	int selectTotalCount(long crewId, String status, String nickname);

	/**
	 * 사용자의 크루 가입 요청을 삭제합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewJoinRequestId 삭제할 요청 ID
	 */
	void deleteCrewJoinRequest(long loginMemberId, long crewJoinRequestId);

	/**
	 * 크루 멤버를 직접 추가합니다. (관리자/리더용)
	 *
	 * @param loginMemberId 요청자 ID
	 * @param memberId 대상 사용자 ID
	 * @param crewId 대상 크루 ID
	 */
	void insertCrewMember(long loginMemberId, long memberId, long crewId);

	/**
	 * 사용자가 자발적으로 크루를 탈퇴합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @param crewId 탈퇴할 크루 ID
	 */
	void deleteCrewMember(long loginMemberId, long crewId);

	/**
	 * 특정 크루 멤버를 강제로 탈퇴시킵니다.
	 *
	 * @param loginMemberId 요청자 ID (크루장)
	 * @param crewId 대상 크루 ID
	 * @param crewMemberId 강퇴할 멤버 ID
	 */
	void forcedRemovalCrewMember(long loginMemberId, long crewId, long crewMemberId);

	/**
	 * 크루 멤버 목록을 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 대상 크루 ID
	 * @param crewMemberSearchFilter 검색 조건
	 * @return 멤버 목록 응답 리스트
	 */
	List<CrewMemberListResponse> selectCrewMembers(
		long loginMemberId,
		long crewId,
		CrewMemberSearchFilter crewMemberSearchFilter);

	/**
	 * 특정 크루 멤버의 크루장 여부를 토글합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param crewId 대상 크루 ID
	 * @param crewMemberId 대상 멤버 ID
	 */
	void updateCrewMemberIsLeader(long loginMemberId, long crewId, long crewMemberId);

	/**
	 * 지역을 기준으로 추천 크루 목록을 조회합니다.
	 *
	 * @param perPage 한 페이지당 결과 수
	 * @param region 추천할 지역 (예: 서울, 부산 등)
	 * @return 추천 크루 리스트
	 */
	List<CrewListResponse> recommendCrewsByRegion(int perPage, String region);

	/**
	 * 현재 로그인한 사용자의 소속 크루 ID를 조회합니다.
	 *
	 * @param loginMemberId 사용자 ID
	 * @return 소속 크루 ID 또는 null
	 */
	Long selectMyCrew(long loginMemberId);

	/**
	 * 주어진 크루 이름이 이미 존재하는지 확인합니다.
	 *
	 * @param crewName 크루 이름
	 * @return true이면 중복 존재, false이면 사용 가능
	 */
	boolean existsByCrewName(String crewName);

	/**
	 * 사용자의 특정 크루 가입 요청 정보를 조회합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param memberId 조회 대상 사용자 ID
	 * @return 가입 요청 소스 정보
	 */
	CrewJoinRequestSource selectCrewJoinRequestByMemberId(long loginMemberId, long memberId);
}