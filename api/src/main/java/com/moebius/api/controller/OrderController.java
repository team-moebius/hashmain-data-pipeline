package com.moebius.api.controller;

import com.moebius.backend.order.PurchaseService;
import com.moebius.backend.order.SaleService;
import com.moebius.backend.order.StoplossService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/order")
@RequiredArgsConstructor
public class OrderController {
	private final PurchaseService purchaseService;
	private final SaleService saleService;
	private final StoplossService stoplossService;
}
