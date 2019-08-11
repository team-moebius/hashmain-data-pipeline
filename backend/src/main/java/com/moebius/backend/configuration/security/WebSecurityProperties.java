package com.moebius.backend.configuration.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "moebius.web-security")
public class WebSecurityProperties {
	private String[] all;
	private String[] admin;
	private String[] member;
}