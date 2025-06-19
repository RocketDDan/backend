package org.hyundae_futurenet.rocketddan.runners_hi.backend.batch;

import java.util.List;
import java.util.Set;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.log.FeedViewLogMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdViewBatchScheduler {

	private final RedisTemplate<String, Object> redisTemplate;

	private final FeedViewLogMapper feedViewLogMapper;

	@Scheduled(fixedRate = 60000)
	public void flushAdViews() {

		log.info("flushAdViews :: start");

		Set<String> keys = redisTemplate.keys("adView:*");
		if (keys == null || keys.isEmpty())
			return;

		for (String key : keys) {
			String[] parts = key.split(":");
			if (parts.length < 2)
				continue;

			Long feedId;
			try {
				feedId = Long.parseLong(parts[1]);
			} catch (NumberFormatException e) {
				continue; // 키 포맷 오류 방지
			}

			// 💡 memberId 문자열 Set 가져오기
			Set<Object> memberSet = redisTemplate.opsForSet().members(key);
			if (memberSet == null || memberSet.isEmpty())
				continue;

			// 💡 삭제는 트랜잭션 처리
			List<Object> result = redisTemplate.execute(new SessionCallback<>() {
				@Override
				public List<Object> execute(RedisOperations operations) throws DataAccessException {

					operations.watch(key);
					operations.multi();
					operations.delete(key);
					return operations.exec();
				}
			});
			
			if (result.isEmpty()) {
				continue;
			}

			// 💾 DB에 기록
			for (Object memberStr : memberSet) {
				String memberKey = String.valueOf(memberStr); // 예: "member_42"
				if (memberKey.startsWith("member_")) {
					try {
						Long memberId = Long.parseLong(memberKey.replace("member_", ""));
						feedViewLogMapper.save(feedId, memberId);
					} catch (NumberFormatException ignore) {
						log.warn("Invalid member format: {}", memberKey);
					}
				} else if (memberKey.startsWith("ip_")) {
					String ip = memberKey.replace("ip_", "");
					feedViewLogMapper.saveIp(feedId, ip);
				}
			}
		}
	}
}