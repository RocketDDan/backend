package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cloudfront")
public class CloudFrontProperties {

	private String distributionDomain;

	private String keyPairId;

	private String privateKeyPath;
}