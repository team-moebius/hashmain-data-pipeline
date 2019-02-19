package com.moebius.api.controller;

import com.moebius.backend.stoploss.StoplossService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1")
public class OrderController {
	private final StoplossService stoplossService;

	public OrderController(StoplossService stoplossService) {
		this.stoplossService = stoplossService;
	}


}
