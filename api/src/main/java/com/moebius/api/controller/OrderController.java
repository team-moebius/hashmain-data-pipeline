package com.moebius.api.controller;

import com.moebius.backend.order.PurchaseService;
import com.moebius.backend.order.SaleService;
import com.moebius.backend.order.StoplossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/v1/order")
public class OrderController {
	private final PurchaseService purchaseService;
	private final SaleService saleService;
	private final StoplossService stoplossService;

	public OrderController(PurchaseService purchaseService, SaleService saleService, StoplossService stoplossService) {
		this.purchaseService = purchaseService;
		this.saleService = saleService;
		this.stoplossService = stoplossService;
	}
}
