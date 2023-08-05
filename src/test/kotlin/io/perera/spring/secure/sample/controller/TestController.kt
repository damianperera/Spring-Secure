package io.perera.spring.secure.sample.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @RequestMapping("/hello")
    fun helloWorld(): String {
        return "Hello World!"
    }
}
