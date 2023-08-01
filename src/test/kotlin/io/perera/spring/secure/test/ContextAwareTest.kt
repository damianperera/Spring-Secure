package io.perera.spring.secure.test

import io.perera.spring.secure.config.SpringSecureAutoConfiguration
import io.perera.spring.secure.dummy.TestApplication
import io.perera.spring.secure.dummy.controller.TestController
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["logging.level.root=DEBUG"])
@AutoConfigureMockMvc
@ContextConfiguration(classes = [TestApplication::class, TestController::class, SpringSecureAutoConfiguration::class])
annotation class ContextAwareTest
