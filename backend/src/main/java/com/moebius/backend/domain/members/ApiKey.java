package com.moebius.backend.domain.members;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ApiKey {
    private String id;
    private String accessKey;
    private String secretKey;
}
