package io.perera.spring.secure.sample

import io.perera.spring.secure.annotation.EnableSpringSecure
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSpringSecure
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
