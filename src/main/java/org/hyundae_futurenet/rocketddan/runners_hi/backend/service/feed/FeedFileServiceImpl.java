package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed.FeedFileMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedFileServiceImpl implements FeedFileService {

	private final FeedFileMapper feedFileMapper;

	@Override
	public void save(long loginMemberId, long feedId, List<String> uploadedfilePathList) {

		// feedFileMapper.insertAll(loginMemberId, feedId, uploadedfilePathList);
		for (int i = 0; i < uploadedfilePathList.size(); i++) {
			feedFileMapper.insert(loginMemberId, feedId, uploadedfilePathList.get(i), i + 1);
		}
	}

	@Override
	public List<String> searchFilePathList(long feedId) {

		return feedFileMapper.selectFilePathList(feedId);
	}

	@Override
	public void deleteAll(long loginMemberId, long feedId) {

		feedFileMapper.deleteAll(loginMemberId, feedId);
	}
}
