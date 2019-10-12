package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Base;
import com.moebius.backend.domain.commons.Exchange;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "orders")
public class Order extends Base {
    @Id
    private ObjectId id;
    private ObjectId apiKeyId;
    private Exchange exchange;
    // Symbol has been changed to String from Enum cause of real time changes in external exchanges.
    private String symbol;
    private OrderType orderType;
    private OrderPosition orderPosition;
    private double price;
    private double volume;
}
