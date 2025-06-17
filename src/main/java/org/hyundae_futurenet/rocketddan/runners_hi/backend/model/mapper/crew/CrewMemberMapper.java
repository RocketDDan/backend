package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;

@Mapper
public interface CrewMemberMapper {

	// 크루장 여부 조회
	boolean isLeaderByMemberIdAndCrewId(@Param("crewId") long crewId, @Param("memberId") long memberId);

	// 크루 멤버 여부 조회
	boolean isCrewMemberByMemberIdAndCrewId(@Param("crewId") long crewId, @Param("memberId") long memberId);

	// 크루 멤버 정보 조회
	Optional<CrewMemberDetailResponse> selectCrewMemberByCrewIdAndMemberId(
		@Param("crewId") long crewId,
		@Param("memberId") long memberId);

	boolean existsLeaderByMemberId(@Param("memberId") long memberId);

	Long findCrewLeaderIdByMemberId(@Param("memberId") Long memberId);

	boolean isCrewMember(@Param("memberId") Long memberId);

	void insertCrewMember(
		@Param("loginMemberId") long loginMemberId,
		@Param("memberId") long memberId,
		@Param("crewId") long crewId,
		@Param("isLeader") boolean isLeader);

	void deleteCrewMember(@Param("crewMemberId") long crewMemberId);

	// 크루원 목록 조회
	List<CrewMemberListResponse> selectCrewMembers(
		@Param("crewId") long crewId,
		@Param("nickname") String nickname,
		@Param("limit") int limit,
		@Param("offset") int offset);

	// 크루장 여부 변경
	void updateCrewMemberIsLeader(
		@Param("crewMemberId") long crewMemberId,
		@Param("isLeader") char isLeader,
		@Param("loginMemberId") long loginMemberId
	);

	// 일반 크루원 여부 조회
	boolean isCrewMemberByCrewMemberIdAndCrewId(
		@Param("crewMemberId") long crewMemberId,
		@Param("crewId") long crewId);

	// 특정 크루의 리더 조회
	Long selectCrewLeaderIdByCrewId(@Param("crewId") long crewId);
}
