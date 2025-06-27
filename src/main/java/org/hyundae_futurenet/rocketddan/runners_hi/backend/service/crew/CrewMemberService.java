package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;

/**
 * 크루 멤버 관리 서비스 인터페이스입니다.
 * 크루원 등록, 탈퇴, 목록 조회 및 크루장 권한 관련 기능을 제공합니다.
 */
public interface CrewMemberService {

	/**
	 * 해당 멤버가 크루장인지 여부를 확인합니다.
	 *
	 * @param memberId 멤버 ID
	 * @param crewId 크루 ID
	 * @return 크루장 여부
	 */
	boolean isLeader(long memberId, long crewId);

	/**
	 * 해당 멤버가 해당 크루의 멤버인지 여부를 확인합니다.
	 *
	 * @param memberId 멤버 ID
	 * @param crewId 크루 ID
	 * @return 멤버 여부
	 */
	boolean isCrewMember(long memberId, long crewId);

	/**
	 * 특정 멤버의 크루 내 상세 정보를 조회합니다.
	 *
	 * @param memberId 멤버 ID
	 * @param crewId 크루 ID
	 * @return 크루 멤버 상세 응답 (없을 경우 Optional.empty)
	 */
	Optional<CrewMemberDetailResponse> selectCrewMember(long memberId, long crewId);

	/**
	 * 새로운 크루 멤버를 생성합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param memberId 추가할 멤버 ID
	 * @param crewId 크루 ID
	 * @param isLeader 크루장 여부
	 */
	void insertCrewMember(long loginMemberId, long memberId, long crewId, boolean isLeader);

	/**
	 * 특정 크루 멤버가 탈퇴합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param crewMemberId 탈퇴할 멤버 ID
	 */
	void deleteCrewMember(long loginMemberId, long crewMemberId);

	/**
	 * 크루 멤버 목록을 조회합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param crewId 크루 ID
	 * @param crewMemberSearchFilter 검색 필터 (닉네임, 가입일 등)
	 * @return 크루 멤버 리스트 응답
	 */
	List<CrewMemberListResponse> selectCrewMembers(
		long loginMemberId,
		long crewId,
		CrewMemberSearchFilter crewMemberSearchFilter
	);

	/**
	 * 특정 멤버의 크루장 권한 여부를 수정합니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param crewMemberId 대상 멤버 ID
	 * @param isLeader 크루장 여부 설정값
	 */
	void updateCrewMemberIsLeader(long loginMemberId, long crewMemberId, boolean isLeader);

	/**
	 * 특정 크루의 현재 크루장 ID를 조회합니다.
	 *
	 * @param crewId 크루 ID
	 * @return 크루장 멤버 ID
	 */
	Long selectCrewLeaderIdByCrewId(long crewId);
}