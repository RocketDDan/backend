package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {

	private final CrewMapper crewMapper;

	@Override
	public long insertCrew(long loginMemberId, CrewCreateRequest crewCreateRequest) {

		long crewId = crewMapper.insertCrew(loginMemberId, crewCreateRequest);
		log.info("CrewService :: insertCrew, crewId = {}", crewId);
		return crewId;
	}

	@Override
	public void updateCrewProfilePath(long crewId, String profilePath) {

		crewMapper.updateCrewProfilePath(crewId, profilePath);
		log.info("CrewService :: updateCrewProfilePath, crewId = {}", crewId);
	}

	@Override
	public void updateCrew(long loginMemberId, long crewId, CrewUpdateRequest crewUpdateRequest, String profilePath) {

		crewMapper.updateCrew(loginMemberId, crewId, crewUpdateRequest, profilePath);
		log.info("CrewService :: updateCrew");
	}

	@Override
	public void deleteCrew(long loginMemberId, long crewId) {

		crewMapper.deleteCrew(crewId);
		log.info("CrewService :: deleteCrew, crewId = {}", crewId);
	}

	@Override
	public Optional<CrewDetailResponse> selectCrewByCrewId(long loginMemberId, long crewId) {

		log.info("CrewService :: selectCrewById, crewId = {}", crewId);
		return crewMapper.selectCrewByCrewId(loginMemberId, crewId);
	}

	@Override
	public List<CrewListResponse> selectCrewsByFilter(long loginMemberId, CrewSearchFilter crewSearchFilter) {

		String name = crewSearchFilter.getCrewName();
		String order = crewSearchFilter.getOrder().name();
		int limit = crewSearchFilter.getPerPage();
		int offset = (crewSearchFilter.getPage() - 1) * limit;
		log.info("CrewService :: selectCrewsByFilter, crewName = {}, option = {}", name, order);

		return crewMapper.selectCrewsByFilter(crewSearchFilter, limit, offset);
	}

	@Override
	public List<CrewListResponse> recommendCrewsByRegion(int perPage, String region) {

		log.info("CrewService :: recommendCrewsByRegion, region = {}, perPage = {}", region, perPage);
		return crewMapper.recommendCrewsByRegion(region, perPage);
	}

	@Override
	public Long selectMyCrew(long loginMemberId) {

		log.info("CrewService :: selectMyCrew");
		return crewMapper.selectMyCrew(loginMemberId);
	}
}
