package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.CompanyOnly;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response.KakaoPayReadyResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Company Feed API", description = "Feed")
@RequiredArgsConstructor
@RestController
@RequestMapping("/company-feeds")
public class CompanyFeedController {

	private final FeedFacade feedFacade;

	@CompanyOnly
	@Operation(summary = "기업의 Feed 업로드", description = "Feed를 결제와 함께 업로드합니다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> requestPayment(
		@Auth final Accessor accessor,
		@RequestParam("content") String content,
		@RequestParam(value = "lat", required = false) Double lat,
		@RequestParam(value = "lng", required = false) Double lng,
		@RequestPart("fileList") List<MultipartFile> fileList,
		@RequestParam("amount") int payAmount
	) {

		log.info("[결제 요청] 멤버 id: {}, 금액: {}", accessor.getMemberId(), payAmount);
		KakaoPayReadyResponse response = feedFacade.uploadFeedByCompany(
			accessor.getMemberId(),
			content,
			lat,
			lng,
			fileList,
			payAmount
		);
		return ResponseEntity.ok(response);
	}

}
