package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMemberMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewMemberServiceImpl implements CrewMemberService {

	private final CrewMemberMapper crewMemberMapper;

	@Override
	public boolean isLeader(long memberId, long crewId) {

		boolean isLeader = crewMemberMapper.isLeaderByMemberIdAndCrewId(crewId, memberId);
		log.info("CrewMemberService :: isLeader = {}, memberId = {}, crewId = {},", isLeader, memberId, crewId);
		return isLeader;
	}

	@Override
	public boolean isCrewMember(long memberId, long crewId) {

		boolean isCrewMember = crewMemberMapper.isCrewMemberByMemberIdAndCrewId(crewId, memberId);
		log.info("CrewMemberService :: isCrewMember = {}, memberId = {}, crewId = {},", isCrewMember, memberId, crewId);
		return isCrewMember;
	}

	@Override
	public Optional<CrewMemberDetailResponse> selectCrewMember(long memberId, long crewId) {

		return crewMemberMapper.selectCrewMemberByCrewIdAndMemberId(crewId, memberId);
	}
}

