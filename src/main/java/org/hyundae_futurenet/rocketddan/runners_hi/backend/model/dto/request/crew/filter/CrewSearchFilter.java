package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "크루 검색 필터 DTO")
public class CrewSearchFilter {

	@Schema(description = "검색할 크루 이름", example = "러닝크루")
	private String crewName = "";

	@Schema(description = "검색할 지역", example = "서울시 동작구")
	private String region = "";

	@Schema(description = "페이지 번호 (1 이상)", example = "1")
	@Min(value = 1, message = "page는 1 이상이어야 합니다.")
	private int page = 1;

	@Schema(description = "페이지당 크루 개수 (1~50)", example = "10")
	@Min(value = 1, message = "perPage는 1 이상이어야 합니다.")
	@Max(value = 50, message = "perPage는 50 이하여야 합니다.")
	private int perPage = 10;

	@Schema(description = "정렬 기준(기본 : LATEST) ", example = "LATEST | OLDEST | MEMBER_CNT")
	@NotNull(message = "정렬은 필수입니다.")
	private CrewOrder order = CrewOrder.LATEST;
}
