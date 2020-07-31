package com.moebius.api

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@Slf4j
@SpringBootTest
class ApiApplicationTest extends Specification{
    def "contextLoads"(){
        expect:
        log.debug("hello, world")
    }
}
