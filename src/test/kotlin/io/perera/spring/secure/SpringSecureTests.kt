package io.perera.spring.secure

import io.perera.spring.secure.domain.OWASPHeader
import io.perera.spring.secure.service.ResponseInterceptor
import io.perera.spring.secure.test.ContextAwareTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.client.RestTemplate

@ContextAwareTest
class SpringSecureTests @Autowired constructor(
    private val mockMvc: MockMvc
) {
    @Test
    fun `test controller returns expected OWASP headers and content`() {
        mockMvc.get("/hello")
            .andExpect { status { isOk() } }
            .andExpect { content { string("Hello World!") } }
            .andExpect { header { string("Cache-Control", "no-store, max-age=0") } }
            .andExpect { header { doesNotExist("Host-Header") } }
    }
}
