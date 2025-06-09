package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;

@Mapper
public interface CrewMapper {

	// 크루 존재 여부
	boolean existsByCrewId(@Param("crewId") long crewId);

	boolean existsByName(@Param("name") String name);

	// 크루 프로필 조회
	Optional<CrewDetailResponse> selectCrewByCrewId(
		@Param("loginMemberId") long loginMemberId,
		@Param("crewId") long crewId);

	// 크루 목록 조회
	List<CrewListResponse> selectCrewsByFilter(
		@Param("req") CrewSearchFilter crewSearchFilter,
		@Param("limit") int limit,
		@Param("offset") int offset);

	// 크루 생성
	long insertCrew(
		@Param("loginMemberId") long loginMemberId,
		@Param("req") CrewCreateRequest crewCreateRequest);

	// 크루 프로필 설정
	void updateCrewProfilePath(@Param("crewId") long crewId, @Param("profilePath") String profilePath);

	// 크루 정보 변경
	void updateCrew(
		@Param("loginMemberId") long loginMemberId,
		@Param("crewId") long crewId,
		@Param("req") CrewUpdateRequest crewUpdateRequest,
		@Param("profilePath") String profilePath);

	// 크루 삭제
	void deleteCrew(@Param("crewId") long crewId);
}
