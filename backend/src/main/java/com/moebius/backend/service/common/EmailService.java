package com.moebius.backend.service.common;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmailService {
	public Mono<ResponseEntity<?>> findPassword(String email) {
		return null;
	}
}
