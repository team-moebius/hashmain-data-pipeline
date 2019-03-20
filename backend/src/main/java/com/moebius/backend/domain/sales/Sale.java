package com.moebius.backend.domain.sales;

import com.moebius.backend.domain.commons.Base;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sales")
@Getter
@Setter
public class Sale extends Base {
}
