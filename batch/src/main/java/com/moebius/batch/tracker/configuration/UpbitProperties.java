package com.moebius.batch.tracker.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "upbit")
public class UpbitProperties {
	private String accessKey;
	private String secretKey;
	private UriInfo uriInfo;

	@Getter
	@Setter
	public static class UriInfo {
		private String openedHost;
		private String secretHost;
		private String asset;
		private String order;
	}
}
