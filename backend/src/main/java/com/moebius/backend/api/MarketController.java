package com.moebius.backend.api;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.dto.MarketDto;
import com.moebius.backend.dto.frontend.response.MarketResponseDto;
import com.moebius.backend.service.market.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/markets")
@RequiredArgsConstructor
public class MarketController {
	private final MarketService marketService;

	@PostMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Mono<ResponseEntity<String>> createMarket(@RequestBody @Valid MarketDto marketDto) {
		return marketService.createMarket(marketDto);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Mono<ResponseEntity<String>> deleteMarket(@PathVariable String id) {
		return marketService.deleteMarket(id);
	}

	@PutMapping("/{exchange}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Mono<ResponseEntity<?>> updateMarketsByExchange(@PathVariable Exchange exchange) {
		return marketService.updateMarketsByExchange(Exchange.getBy(exchange.toString()));
	}

	@GetMapping("/{exchange}")
	@PreAuthorize("hasAuthority('MEMBER')")
	public Mono<ResponseEntity<MarketResponseDto>> getMarketsByExchange(@PathVariable String exchange) {
		return null;
	}
}
