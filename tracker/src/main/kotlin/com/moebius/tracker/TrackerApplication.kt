package com.moebius.tracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class TrackerApplication
fun main(args: Array<String>) {
    runApplication<TrackerApplication>(*args)
}
