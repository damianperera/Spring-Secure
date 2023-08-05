package io.perera.spring.secure.test

import io.perera.spring.secure.sample.TestApplication
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["logging.level.root=DEBUG"],
    classes = [TestApplication::class]
)
@AutoConfigureMockMvc
annotation class ContextAwareTest
