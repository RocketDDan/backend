package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;

public interface CrewService {

	// 크루 생성
	long insertCrew(long loginMemberId, CrewCreateRequest crewCreateRequest);

	// 크루 프로필 지정
	void updateCrewProfilePath(long crewId, String profilePath);

	// 크루 수정
	void updateCrew(long loginMemberId, long crewId, CrewUpdateRequest crewUpdateRequest, String profilePath);

	// 크루 삭제
	void deleteCrew(long loginMemberId, long crewId);

	// 크루 프로필 조회
	Optional<CrewDetailResponse> selectCrewByCrewId(long loginMemberId, long crewId);

	// 크루 목록 조회
	List<CrewListResponse> selectCrewsByFilter(long loginMemberId, CrewSearchFilter crewSearchFilter);

	// 크루 지역별 조회
	List<CrewListResponse> recommendCrewsByRegion(int perPage, String region);
}
