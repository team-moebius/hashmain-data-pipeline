package com.moebius.backend.domain.trades;

import com.moebius.backend.domain.commons.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "trades")
public class Trade extends Base {
	@Id
	private ObjectId id;
	private Exchange exchange;
	private Symbol symbol;
	private TradeType tradeType;
	private Change change;
	private double price;
	private double volume;
	private double prevClosingPrice;
	private double changePrice;
}
