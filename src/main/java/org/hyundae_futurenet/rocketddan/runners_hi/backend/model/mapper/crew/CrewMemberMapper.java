package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;

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

	boolean existsLeaderByMemberId(@Param("memberId") Long memberId);

	Long findCrewLeaderIdByMemberId(@Param("memberId") Long memberId);

	boolean isCrewMember(@Param("memberId") Long memberId);
}

