package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedSearchFilter {

	@NotNull(message = "scope는 필수입니다.")
	private FeedScope scope;

	@NotNull(message = "정렬은 필수입니다.")
	private FeedOrder order;

	@Min(value = 1, message = "page는 1 이상이어야 합니다.")
	private int page;

	@Min(value = 1, message = "perPage는 1 이상이어야 합니다.")
	@Max(value = 50, message = "perPage는 50 이하여야 합니다.")
	private int perPage;

	@Getter
	public enum FeedScope {
		ALL_EXCEPT_ME,
		MY_CREW,
		ME,
	}

	@Getter
	public enum FeedOrder {
		LATEST,
		OLDEST,
		NAME_ASC,
		NAME_DESC,
		MOST_LIKED,
		MOST_COMMENTED,
		RELEVANT,
	}
}
