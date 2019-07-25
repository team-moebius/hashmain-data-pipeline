package com.moebius.backend.domain.apikeys;

import com.moebius.backend.domain.commons.Base;
import com.moebius.backend.domain.commons.Exchange;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "apikeys")
public class ApiKey extends Base {
    @Id
    private ObjectId id;
    private ObjectId memberId;
    private Exchange exchange;
    private String name;
    private String accessKey;
    private String secretKey;
}
