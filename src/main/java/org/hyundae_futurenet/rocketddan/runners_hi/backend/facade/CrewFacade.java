package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;
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

	// 가입 요청
	void insertCrewJoinRequest(long loginMemberId, long crewId, CrewJoinRequest crewJoinRequest);

	// 가입 요청 상태 변경
	void updateCrewJoinRequest(long loginMemberId, long crewId, long crewJoinRequestId, CrewJoinRequestStatus status);

	// 가입 요청 목록 조회
	List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(
		long loginMemberId,
		long crewId,
		CrewJoinRequestSearchFilter crewJoinRequestSearchFilter);

	// 크루 멤버 생성
	void insertCrewMember(long loginMemberId, long memberId, long crewId);

	// 크루 멤버 탈퇴
	void deleteCrewMember(long loginMemberId, long crewId);

	// 크루 멤버 강퇴
	void forcedRemovalCrewMember(long loginMemberId, long crewId, long crewMemberId);

	// 크루원 목록 조회
	List<CrewMemberListResponse> selectCrewMembers(
		long loginMemberId,
		long crewId,
		CrewMemberSearchFilter crewMemberSearchFilter);

	// 크루장 여부 변경
	void updateCrewMemberIsLeader(long loginMemberId, long crewId, long crewMemberId);

	// 크루 지역별 추천
	List<CrewListResponse> selectCrewsByRegion(String region);
}

