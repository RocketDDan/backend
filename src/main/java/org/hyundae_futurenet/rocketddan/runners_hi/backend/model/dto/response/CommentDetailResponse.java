package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentDetailResponse {

	private long commentId;

	private String content;

	private long writerId;

	private String writerNickname;

	private String writerProfileUrl;

	private String createdAt;

	private String updatedAt;

	private boolean isUpdated;

	private boolean isMine;
}
