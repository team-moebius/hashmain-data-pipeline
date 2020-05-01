package com.moebius.backend.assembler.exchange;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderStatus;
import com.moebius.backend.domain.orders.OrderType;
import com.moebius.backend.dto.OrderStatusDto;
import com.moebius.backend.dto.exchange.upbit.UpbitOrderDto;
import com.moebius.backend.dto.exchange.upbit.UpbitOrderStatusDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UpbitAssembler implements ExchangeAssembler {
	private static final String ORDER_POSITION_BID = "bid";
	private static final String ORDER_POSITION_ASK = "ask";
	private static final String ORDER_TYPE_LIMIT = "limit";
	private static final String ORDER_TYPE_PRICE = "price";
	private static final String ORDER_TYPE_MARKET = "market";
	private static final String WAIT_STATE = "wait";

	public UpbitOrderDto toOrderDto(Order order) {
		UpbitOrderDto upbitOrderDto = new UpbitOrderDto();
		upbitOrderDto.setId(order.getId().toHexString());
		upbitOrderDto.setSymbol(order.getSymbol());
		upbitOrderDto.setOrderPosition(parseOrderPosition(order.getOrderPosition()));
		upbitOrderDto.setOrderType(parseOrderType(order.getOrderPosition(), order.getOrderType()));
		upbitOrderDto.setPrice(order.getPrice());
		upbitOrderDto.setVolume(order.getVolume());

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

	public OrderStatusDto toOrderStatusDto(Order order, UpbitOrderStatusDto upbitOrderStatusDto) {
		return OrderStatusDto.builder()
			.id(order.getId().toHexString())
			.orderStatus(parseOrderStatus(upbitOrderStatusDto.getState()))
			.build();
	}

	private OrderStatus parseOrderStatus(String state) {
		if (StringUtils.isEmpty(state)) {
			return OrderStatus.STOPPED;
		}

		if (WAIT_STATE.equals(state)) {
			return OrderStatus.IN_PROGRESS;
		}

		return OrderStatus.DONE;
	}
}
