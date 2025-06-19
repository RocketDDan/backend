package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.log;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedViewLogServiceImpl implements FeedViewLogService {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void addViewLog(long memberId, long feedId) {

		String key = "adView:" + feedId;
		String value = "member_" + memberId;

		redisTemplate.opsForSet().add(key, value);      // SADD
		redisTemplate.expire(key, java.time.Duration.ofSeconds(600));  // EXPIRE 600초
	}

	@Override
	public void addViewLogByIp(String ip, long feedId) {

		String key = "adView:" + feedId;
		String value = "ip_" + ip;

		redisTemplate.opsForSet().add(key, value);      // SADD
		redisTemplate.expire(key, java.time.Duration.ofSeconds(600));  // EXPIRE 600초
	}
}