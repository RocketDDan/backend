package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.feed;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/view-logs/feeds")
@RequiredArgsConstructor
public class FeedViewLogController {

	private final FeedFacade feedFacade;

	@PostMapping("/{feed-id}")
	// @NotGuest
	public ResponseEntity<?> postFeedViewLog(
		@Auth Accessor accessor,
		@PathVariable("feed-id") long feedId,
		HttpServletRequest request
	) {

		if (accessor.getMemberId() == null || accessor.getMemberId() == 0) {
			String ip = request.getHeader("X-Forwarded-For"); // 프록시 환경일 경우
			if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr(); // 기본 IP
			}
			log.info("ip : {}", ip);
			feedFacade.addViewLogByIp(ip, feedId);
		} else {
			feedFacade.addViewLog(accessor.getMemberId(), feedId);
		}
		return ResponseEntity.ok().build();
	}
}
