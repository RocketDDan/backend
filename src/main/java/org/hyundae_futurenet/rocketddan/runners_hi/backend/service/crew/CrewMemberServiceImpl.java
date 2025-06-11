package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
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

		log.info("CrewMemberService :: selectCrewMember, memberId = {}, crewId = {}", memberId, crewId);
		return crewMemberMapper.selectCrewMemberByCrewIdAndMemberId(crewId, memberId);
	}

	@Override
	public void insertCrewMember(long loginMemberId, long memberId, long crewId) {

		log.info("CrewMemberService :: insertCrewMember, memberId = {}, crewId = {}", memberId, crewId);
		crewMemberMapper.insertCrewMember(loginMemberId, memberId, crewId);
	}

	@Override
	public void deleteCrewMember(long loginMemberId, long crewMemberId) {

		log.info("CrewMemberService :: deleteCrewMember crewMemberId = {}", crewMemberId);
		crewMemberMapper.deleteCrewMember(crewMemberId);
	}

	@Override
	public List<CrewMemberListResponse> selectCrewMembers(
		long loginMemberId,
		long crewId,
		CrewMemberSearchFilter crewMemberSearchFilter) {

		String nickname = crewMemberSearchFilter.getNickname();
		int limit = crewMemberSearchFilter.getPerPage();
		int offset = (crewMemberSearchFilter.getPage() - 1) * limit;
		log.info("CrewMemberService :: selectCrewMembers, crewId = {}, nickname = {}", crewId, nickname);

		return crewMemberMapper.selectCrewMembers(crewId, nickname, limit, offset);
	}

	@Override
	@Transactional
	public void updateCrewMemberIsLeader(long loginMemberId, long crewMemberId, boolean isLeader) {

		char leader = isLeader ? 'Y' : 'N';
		crewMemberMapper.updateCrewMemberIsLeader(crewMemberId, leader, loginMemberId);
	}
}

