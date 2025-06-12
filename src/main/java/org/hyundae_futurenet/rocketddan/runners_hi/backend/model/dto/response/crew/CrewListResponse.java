package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "크루 목록 조회 응답 DTO")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CrewListResponse {

	@Schema(description = "크루 ID", example = "1")
	private Long crewId;

	@Schema(description = "크루 이름", example = "러닝메이트")
	private String crewName;

	@Schema(description = "크루 주소", example = "서울특별시 강남구 테헤란로 123")
	private String crewAddress;

	@Schema(description = "크루 지역", example = "서울특별시 강남구")
	private String crewRegion;

	@Schema(description = "크루 프로필 이미지 경로", example = "crew/profile/1.jpg")
	private String profilePath;

	@Schema(description = "전체 멤버 수", example = "10")
	private int totalMemberCnt;

	@Schema(description = "크루 소개 미리보기", example = "시대의 주인공은 우리들! 우리는 천하무적! 로켓단! 누구냐고 물으....")
	private String introduce;

	@Schema(description = "크루 생성일", example = "2025-06-05")
	private String createdAt;
}