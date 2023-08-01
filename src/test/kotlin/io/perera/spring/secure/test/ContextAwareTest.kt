package io.perera.spring.secure.test

import io.perera.spring.secure.dummy.TestApplication
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["logging.level.root=DEBUG"])
@AutoConfigureMockMvc
@ContextConfiguration(classes = [TestApplication::class])
annotation class ContextAwareTest
