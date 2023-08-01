package io.perera.spring.secure

import io.perera.spring.secure.test.ContextAwareTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@ContextAwareTest
class SpringSecureTests @Autowired constructor(
    private val mockMvc: MockMvc
) {
    @Test
    fun `test controller returns expected content`() {
        mockMvc.get("/hello")
            .andExpect { status { isOk() } }
            .andExpect { content { string("Hello World!") } }
    }

    @Test
    fun `test controller returns expected OWASP headers`() {
        mockMvc.get("/hello")
            .andExpect { header { string("Cache-Control", "no-store, max-age=0") } }
    }
}
