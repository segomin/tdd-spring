package me.segomin.school.rest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SchoolControllerTests {


    @Autowired
    lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    internal fun aboutReturnsSchoolInfo() {
        mockMvc.perform(get("/about"))
            .andExpect(status().isOk)
            .andExpect(content().string("Welcome to TDD School"))
    }
}