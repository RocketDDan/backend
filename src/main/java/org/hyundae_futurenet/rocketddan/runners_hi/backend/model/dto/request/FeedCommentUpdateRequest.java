package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedCommentUpdateRequest {

	private String newComment;
}
