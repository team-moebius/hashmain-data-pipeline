package com.moebius.backend.domain.stoplosses;

import com.moebius.backend.domain.commons.Base;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.Symbol;
import com.moebius.backend.domain.commons.TradeType;
import com.moebius.backend.domain.members.Member;
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
    private Member user;
    private Exchange exchange;
    private Symbol symbol;
    private TradeType criteria;
    private double price;
    private double volume;
}
