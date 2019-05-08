package com.moebius.backend.domain.members;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ApiKey {
    @Id
    private String id;
    private String accessKey;
    private String secretKey;
}
