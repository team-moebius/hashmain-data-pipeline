package com.moebius.backend.domain.purchases;

import com.moebius.backend.domain.commons.Base;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "purchases")
@Getter
@Setter
public class Purchase extends Base {
}
