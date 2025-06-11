package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "크루 정보 수정 요청 DTO")
public record CrewUpdateRequest(

	@Schema(description = "크루 이름", example = "러닝메이트")
	@Size(min = 1, max = 255, message = "크루명은 최대 255자입니다.")
	@NotBlank(message = "크루명은 필수입니다.")
	String crewName,

	@Schema(description = "크루 소개", example = "같이 달리고 성장하는 러닝 크루입니다.")
	@Size(min = 1, max = 255, message = "크루 소개는 최대 255자입니다.")
	String crewIntroduce,

	@Schema(description = "크루 지역", example = "서울특별시 강남구")
	@Size(min = 1, max = 100, message = "크루 지역은 최대 100자입니다.")
	@NotBlank(message = "크루 지역은 필수입니다.")
	String crewRegion,

	@Schema(description = "크루 주소", example = "서울특별시 강남구 테헤란로 123")
	@Size(min = 1, max = 255, message = "크루 주소는 최대 255자입니다.")
	@NotBlank(message = "크루 주소는 필수입니다.")
	String crewAddress,

	@Schema(description = "크루 프로필 이미지 주소", example = "profile/img.jpg")
	String profilePath
) {

}
