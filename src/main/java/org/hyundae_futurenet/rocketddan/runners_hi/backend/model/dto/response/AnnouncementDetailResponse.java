package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnnouncementDetailResponse {

	private Long announcementId;

	private String title;

	private String content;

	private String attachPath;

	private String createdAt;

	private String updatedAt;

	private String nickname;
}
