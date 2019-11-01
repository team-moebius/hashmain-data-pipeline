package com.moebius.backend.assembler.exchange;

import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderType;
import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.dto.exchange.UpbitOrderDto;
import org.springframework.stereotype.Component;

@Component
public class UpbitAssembler implements ExchangeAssembler {
	private static final String ORDER_POSITION_BID = "bid";
	private static final String ORDER_POSITION_ASK = "ask";
	private static final String ORDER_TYPE_LIMIT = "limit";
	private static final String ORDER_TYPE_PRICE = "price";
	private static final String ORDER_TYPE_MARKET = "market";

	public UpbitOrderDto toOrderDto(OrderDto orderDto) {
		UpbitOrderDto upbitOrderDto = new UpbitOrderDto();
		upbitOrderDto.setId(orderDto.getId());
		upbitOrderDto.setSymbol(orderDto.getSymbol());
		upbitOrderDto.setOrderPosition(parseOrderPosition(orderDto.getOrderPosition()));
		upbitOrderDto.setOrderType(parseOrderType(orderDto.getOrderPosition(), orderDto.getOrderType()));
		upbitOrderDto.setPrice(orderDto.getPrice());
		upbitOrderDto.setVolume(orderDto.getVolume());

		return upbitOrderDto;
	}

	private String parseOrderPosition(OrderPosition orderPosition) {
		if (orderPosition == OrderPosition.PURCHASE) {
			return ORDER_POSITION_BID;
		}
		return ORDER_POSITION_ASK;
	}

	private String parseOrderType(OrderPosition orderPosition, OrderType orderType) {
		if (orderType == OrderType.MARKET) {
			if (orderPosition == OrderPosition.PURCHASE) {
				return ORDER_TYPE_PRICE; // 시장가 매수
			}
			return ORDER_TYPE_MARKET; // 시장가 매도
		}
		return ORDER_TYPE_LIMIT; // 지정가 매수, 매도
	}
}
