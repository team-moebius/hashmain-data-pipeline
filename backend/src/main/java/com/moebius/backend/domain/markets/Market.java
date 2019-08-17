package com.moebius.backend.domain.markets;

import com.moebius.backend.domain.commons.Base;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.Symbol;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "markets")
@CompoundIndex(def = "{'exchange': 1, 'symbol': 1}", unique = true)
public class Market extends Base {
	@Id
	private ObjectId id;
	private Exchange exchange;
	private Symbol symbol;
}
