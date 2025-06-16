package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyWalletListResponse {

	private List<MyWalletResponse> feeds;

	private int totalCount;
}
