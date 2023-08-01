package io.perera.spring.secure

import io.perera.spring.secure.dummy.TestApplication
import io.perera.spring.secure.dummy.controller.TestController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = [TestApplication::class, TestController::class])
class SpringSecureTests @Autowired constructor(
    private val mockMvc: MockMvc
) {
    @Test
    fun `test controller returns expected content`() {
        mockMvc.get("/hello")
            .andExpect { status { isOk() } }
            .andExpect { content { string("Hello World!") } }
    }
}
