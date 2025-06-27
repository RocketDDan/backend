package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;

/**
 * 크루(러닝 그룹) 관리 서비스 인터페이스입니다.
 * 크루의 생성, 수정, 삭제, 조회, 추천 등 주요 기능을 제공합니다.
 */
public interface CrewService {

	/**
	 * 크루를 생성합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewCreateRequest 크루 생성 요청 정보
	 * @return 생성된 크루 ID
	 */
	long insertCrew(long loginMemberId, CrewCreateRequest crewCreateRequest);

	/**
	 * 크루의 프로필 이미지 경로를 지정합니다.
	 *
	 * @param crewId 크루 ID
	 * @param profilePath 이미지 경로 (예: S3 경로)
	 */
	void updateCrewProfilePath(long crewId, String profilePath);

	/**
	 * 크루 정보를 수정합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 수정 대상 크루 ID
	 * @param crewUpdateRequest 수정 요청 정보
	 * @param profilePath 새 프로필 이미지 경로
	 */
	void updateCrew(long loginMemberId, long crewId, CrewUpdateRequest crewUpdateRequest, String profilePath);

	/**
	 * 크루를 삭제합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 삭제할 크루 ID
	 */
	void deleteCrew(long loginMemberId, long crewId);

	/**
	 * 특정 크루의 상세 정보를 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewId 조회할 크루 ID
	 * @return 크루 상세 응답 (Optional)
	 */
	Optional<CrewDetailResponse> selectCrewByCrewId(long loginMemberId, long crewId);

	/**
	 * 필터 조건에 따른 크루 목록을 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param crewSearchFilter 검색 필터 (지역, 키워드 등)
	 * @return 크루 목록
	 */
	List<CrewListResponse> selectCrewsByFilter(long loginMemberId, CrewSearchFilter crewSearchFilter);

	/**
	 * 특정 지역 기반의 추천 크루 목록을 조회합니다.
	 *
	 * @param perPage 페이지당 항목 수
	 * @param region 지역명
	 * @return 추천 크루 목록
	 */
	List<CrewListResponse> recommendCrewsByRegion(int perPage, String region);

	/**
	 * 사용자가 소속된 크루 ID를 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @return 크루 ID (없을 경우 null)
	 */
	Long selectMyCrew(long loginMemberId);

	/**
	 * 입력한 크루명이 이미 존재하는지 확인합니다.
	 *
	 * @param crewName 중복 확인할 크루명
	 * @return true이면 중복, false이면 사용 가능
	 */
	boolean existsByCrewName(String crewName);
}