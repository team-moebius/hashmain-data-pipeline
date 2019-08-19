package com.moebius.backend.domain.stoplosses;

import com.moebius.backend.domain.commons.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "stoplosses")
public class Stoploss extends Base {
    @Id
    private ObjectId id;
    private ObjectId apiKeyId;
    private Exchange exchange;
    private Symbol symbol;
    private TradeType tradeType;
    private OrderType orderType;
    private double price;
    private double volume;
}
