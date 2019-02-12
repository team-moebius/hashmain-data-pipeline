package com.moebius.backend.database.trades;

import com.moebius.backend.database.commons.Base;
import com.moebius.backend.database.commons.Exchange;
import com.moebius.backend.database.commons.Symbol;
import com.moebius.backend.database.commons.TradeType;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trades")
@Getter
@Setter
public class Trade extends Base {
	@Id
	private ObjectId id;
	private Exchange exchange;
	private Symbol symbol;
	private TradeType tradeType;
	private double price;
	private double volume;
}
