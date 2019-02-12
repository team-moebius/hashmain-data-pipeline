package com.moebius.backend.database.stoplosses;

import com.moebius.backend.database.commons.Base;
import com.moebius.backend.database.commons.Exchange;
import com.moebius.backend.database.commons.Symbol;
import com.moebius.backend.database.trades.Type;
import com.moebius.backend.database.users.User;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stoplosses")
@Getter
@Setter
public class Stoploss extends Base {
    @Id
    private ObjectId id;
    private User user;
    private Exchange exchange;
    private Symbol symbol;
    private Type criteria;
    private double price;
    private double volume;
}
