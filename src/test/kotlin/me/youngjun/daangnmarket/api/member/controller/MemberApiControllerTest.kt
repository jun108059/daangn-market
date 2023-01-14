package me.youngjun.daangnmarket.api.member.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import me.youngjun.daangnmarket.api.member.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.member.service.MemberService
import me.youngjun.daangnmarket.infra.jwt.TokenDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockKExtension::class)
class MemberApiControllerTest {

    @MockK
    private lateinit var memberService: MemberService

    @InjectMockKs
    private lateinit var memberApiController: MemberApiController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberApiController).build()
    }

    @Test
    @DisplayName("로그인 테스트")
    fun loginTest() {

        val objectMapper = ObjectMapper();
        // given
        val loginDto = LoginRequestDto("youngjun108059@gmail.com", "1234")
        val loginDtoJson = objectMapper.writeValueAsString(loginDto)
        val tokenDto = TokenDto(
            grantType = "Bearer",
            accessToken = "test123token",
            accessTokenExpiresIn = 1673557504848
        )

        every {
            memberService.loginByEmail(loginDto)
        } returns tokenDto

        // when
        val resultAction = mockMvc.perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJson)
        )

        // then
        resultAction
            .andExpect(status().isOk)
            .andDo { MockMvcResultHandlers.print() }
    }

}