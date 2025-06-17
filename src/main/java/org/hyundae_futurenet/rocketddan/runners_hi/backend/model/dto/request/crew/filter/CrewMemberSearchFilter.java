package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "크루원 검색 필터 DTO")
public class CrewMemberSearchFilter {

	@Schema(description = "크루원 닉네임 검색", example = "User")
	@Max(value = 255, message = "닉네임은 최대 50자입니다.")
	private String nickname = "";

	@Schema(description = "페이지 번호 (1 이상)", example = "1")
	@Min(value = 1, message = "page는 1 이상이어야 합니다.")
	private int page = 1;

	@Schema(description = "페이지당 크루원 개수 (1~50)", example = "10")
	@Min(value = 1, message = "perPage는 1 이상이어야 합니다.")
	@Max(value = 50, message = "perPage는 50 이하여야 합니다.")
	private int perPage = 10;
}
