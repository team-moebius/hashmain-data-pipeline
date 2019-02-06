package com.moebius.backend.database.purchases;

import com.moebius.backend.database.Base;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "purchases")
@Getter
@Setter
public class Purchase extends Base {
}
