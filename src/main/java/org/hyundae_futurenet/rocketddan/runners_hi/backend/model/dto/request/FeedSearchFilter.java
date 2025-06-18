package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.FeedOrder;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.FeedScope;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedSearchFilter {

	@NotNull(message = "scope는 필수입니다.")
	@Schema(description = "조회 범위", example = "ME", implementation = FeedScope.class)
	private FeedScope scope;

	@NotNull(message = "정렬은 필수입니다.")
	@Schema(description = "조회 범위", example = "LATEST", implementation = FeedOrder.class)
	private FeedOrder order;

	@Min(value = 1, message = "page는 1 이상이어야 합니다.")
	@Schema(example = "1")
	private int page;

	@Min(value = 10, message = "perPage는 10 이상이어야 합니다.")
	@Max(value = 50, message = "perPage는 50 이하여야 합니다.")
	@Schema(example = "10")
	private int perPage;

	@Schema(description = "크루 id로 조회할 때 필요하다.")
	private Long crewId;

	@Schema(description = "멤버 id로 조회할 때 필요하다.")
	private Long memberId;
}
