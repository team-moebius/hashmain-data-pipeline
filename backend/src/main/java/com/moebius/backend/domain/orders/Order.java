package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Base;
import com.moebius.backend.domain.commons.Exchange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(exclude = "apiKeyId")
@Document(collection = "orders")
@CompoundIndexes({
    @CompoundIndex(def = "{'price': 1, 'symbol': 1, 'orderPosition': 1, 'orderStatus': 1, 'orderType': 1, 'exchange': 1}"),
    @CompoundIndex(def = "{'symbol': 1, 'exchange': 1, 'orderStatus': 1}")
})
public class Order extends Base {
    @Id
    private ObjectId id;
    @Indexed
    private ObjectId apiKeyId;
    private Exchange exchange;
    // Symbol has been changed to String from Enum cause of real time changes in external exchanges.
    private String symbol;
    private OrderPosition orderPosition;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private double price;
    private double volume;
    private int level;
}
