package com.moebius.backend.domain.stoplosses;

import com.moebius.backend.domain.commons.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Symbol is pure String type cause of changes in real-time by external exchanges.
 */
@Getter
@Setter
@Document(collection = "stoplosses")
public class Stoploss extends Base {
    @Id
    private ObjectId id;
    private ObjectId apiKeyId;
    private Exchange exchange;
    private String symbol;
    private TradeType tradeType;
    private OrderType orderType;
    private double price;
    private double volume;
}
