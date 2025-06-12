package org.hyundae_futurenet.rocketddan.runners_hi.backend.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

	private final AppProperties appProperties;

	@Bean
	public OpenAPI api() {

		final Info info = new Info()
			.title("runners-hi API")
			.description("runners-hi API 명세서입니다.");
		final String serverUrl = appProperties.getServerDomain();

		return new OpenAPI()
			.info(info)
			.servers(Collections.singletonList(new Server()
				.url(serverUrl)
				.description("runners-hi API Server")));
	}
}
