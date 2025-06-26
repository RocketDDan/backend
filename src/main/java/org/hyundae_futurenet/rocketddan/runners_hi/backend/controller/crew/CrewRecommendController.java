package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.crew;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.CrewFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/crews/recommend")
@RequiredArgsConstructor
@Tag(name = "CREW Recommend API", description = "Crew Recommend 요청")
@Slf4j
public class CrewRecommendController {

	private final CrewFacade crewFacade;

	@Operation(summary = "크루 지역 추천", description = "지역 기반 크루 랜덤 추천")
	@GetMapping
	public ResponseEntity<List<CrewListResponse>> recommendCrews(
		@Parameter(
			name = "perPage",
			description = "한 페이지에 보여줄 크루의 개수 (기본값 9)",
			required = false
		)
		@RequestParam(value = "perPage", defaultValue = "9") int perPage,

		@Parameter(
			name = "region",
			description = "추천받을 지역",
			example = "서울특별시 강남구",
			required = false
		)
		@RequestParam(value = "region", required = false) String region) {

		List<CrewListResponse> result = crewFacade.recommendCrewsByRegion(perPage, region);
		return ResponseEntity.ok(result);
	}

}