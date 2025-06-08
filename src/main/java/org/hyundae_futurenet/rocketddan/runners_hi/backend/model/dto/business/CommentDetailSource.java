package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDetailSource {

	private long commentId;

	private String content;

	private long writerId;

	private String writerNickname;

	private String writerProfilePath;

	private String createdAt;

	private String updatedAt;

	private boolean isUpdated;

	private boolean isMine;
}
