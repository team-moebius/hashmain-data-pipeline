package com.moebius.backend.api;

import com.moebius.backend.service.order.PurchaseService;
import com.moebius.backend.service.order.SaleService;
import com.moebius.backend.service.order.StoplossService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
	private final PurchaseService purchaseService;
	private final SaleService saleService;
	private final StoplossService stoplossService;
}
