package com.moebius.api

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ApiApplicationTest extends Specification{
    def "contextLoads"(){
        expect:
        log.debug("hello, world")
    }
}
