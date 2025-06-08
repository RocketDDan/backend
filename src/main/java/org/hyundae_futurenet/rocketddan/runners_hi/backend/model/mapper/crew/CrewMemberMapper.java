package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CrewMemberMapper {

	public boolean existsLeaderByMemberId(@Param("memberId") Long memberId);

	Long findCrewLeaderIdByMemberId(@Param("memberId") Long memberId);

	boolean isCrewMember(@Param("memberId") Long memberId);
}
