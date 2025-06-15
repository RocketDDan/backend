package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "크루 생성 요청 DTO")
@Getter
@Setter
public class CrewCreateRequest {

	private Long crewId;

	@Schema(description = "크루 이름", example = "러닝크루")
	@Size(min = 1, max = 50, message = "크루명은 최대 50자입니다.")
	@NotBlank(message = "크루명은 필수입니다.")
	private String crewName;

	@Schema(description = "크루 소개", example = "건강한 러닝 문화를 함께 만들어가요!")
	@Size(min = 1, max = 255, message = "크루 소개는 최대 255자입니다.")
	private String crewIntroduce;

	@Schema(description = "크루 지역", example = "서울특별시 강남구")
	@Size(min = 1, max = 50, message = "크루 지역은 최대 50자입니다.")
	@NotBlank(message = "크루 지역은 필수입니다.")
	private String crewRegion;

	@Schema(description = "크루 주소", example = "서울특별시 강남구 테헤란로 123")
	@Size(min = 1, max = 50, message = "크루 주소는 최대 50자입니다.")
	@NotBlank(message = "크루 주소는 필수입니다.")
	private String crewAddress;
}