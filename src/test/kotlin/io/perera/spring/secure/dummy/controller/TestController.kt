package io.perera.spring.secure.dummy.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @RequestMapping("/hello")
    fun helloWorld(): String {
        return "Hello World!"
    }
}
