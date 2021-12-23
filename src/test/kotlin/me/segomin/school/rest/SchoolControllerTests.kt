package me.segomin.school.rest

import me.segomin.school.dto.ClassName
import me.segomin.school.dto.Principal
import me.segomin.school.dto.Student
import me.segomin.school.dto.Teacher
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
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
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    internal fun aboutReturnsSchoolInfo() {
        mockMvc.perform(get("/about"))
            .andExpect(status().isOk)
            .andExpect(content().string("Welcome to TDD School"))
    }

    @Test
    internal fun greetingReturnsHelloAndUsername() {
        mockMvc.perform(get("/greeting").with(user("Sego")))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello, Sego"))
    }

    @Test
    internal fun greetingWhenUnauthenticatedUserThenReturns401() {
        mockMvc.perform(get("/greeting"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    internal fun scoresWhenUserIsTeacherReturnsListOfScores() {
        val joe = Teacher("Joe", "A", ClassName.Alpha)
        mockMvc.perform(get("/scores/${joe.className}").with(user(SchoolMemberService.SchoolMemberDetails(joe))))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Int>(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.`is`("${joe.className} A 100")))
    }

    @Test
    internal fun scoresWhenUserIsNotTeacherReturns403() {
        val sego = Student("Min", "A", ClassName.Alpha)
        mockMvc.perform(
            get("/scores/${sego.className}")
                .with(user(SchoolMemberService.SchoolMemberDetails(sego)))
        ).andExpect(status().isForbidden)
    }

    @Test
    internal fun scoresWhenUnauthenticatedUserReturns401() {
        mockMvc.perform(get("/scores/Alpha"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    internal fun postAboutWhenUserIsAdminThenUpdatesSchoolInfo() {
        val skinner = Principal("Skinner", "A")
        mockMvc.perform(
            post("/about")
                .content("Welcome to BDD School")
                .with(user(SchoolMemberService.SchoolMemberDetails(skinner)))
                .with(csrf())
        ).andExpect(status().isOk)
    }
}
