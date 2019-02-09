package com.moebius.backend.database.stoplosses;

import com.moebius.backend.database.Base;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stoplosses")
@Getter
@Setter
public class Stoploss extends Base {
}
