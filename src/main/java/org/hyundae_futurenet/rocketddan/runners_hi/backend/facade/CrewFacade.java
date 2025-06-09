package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CrewFacade {

	// 크루 생성
	long insertCrew(long loginMemberId, CrewCreateRequest crewCreateRequest, MultipartFile file);

	// 크루 수정
	void updateCrew(long loginMemberId, long crewId, CrewUpdateRequest crewUpdateRequest, MultipartFile file);

	// 크루 삭제
	void deleteCrew(long loginMemberId, long crewId);

	// 크루 프로필 조회
	CrewDetailResponse selectCrewByCrewId(long loginMemberId, long crewId);

	// 크루 목록 조회
	List<CrewListResponse> selectCrewsByFilter(long loginMemberId, CrewSearchFilter crewSearchFilter);
}

