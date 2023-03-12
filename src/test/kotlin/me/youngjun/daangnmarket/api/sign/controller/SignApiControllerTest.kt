package me.youngjun.daangnmarket.api.sign.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.youngjun.daangnmarket.api.sign.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.sign.dto.LoginResponseDto
import me.youngjun.daangnmarket.api.sign.service.SignService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class SignApiControllerTest : DescribeSpec({
    val signService = mockk<SignService>()
    val target = SignApiController(signService)
    val mockMvc = MockMvcBuilders.standaloneSetup(target).build()

    describe("로그인 요청") {
        every {
            signService.loginByEmail(loginDto)
        } returns loginResponseDto
        val resultAction = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJson),
        )

        context("ID/PW가 일치하면") {

            it("서비스를 통해 데이터를 조회한다") {
                verify(exactly = 1) {
                    signService.loginByEmail(any())
                }
            }
            it("요청은 성공한다") {
                resultAction
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andDo { MockMvcResultHandlers.print() }
            }
            it("반환 데이터는 JSON 이다") {
                resultAction
                    .andExpect(
                        MockMvcResultMatchers
                            .content().contentType(MediaType.APPLICATION_JSON),
                    )
            }
        }
    }
}) {

    companion object {
        private val objectMapper = ObjectMapper()
        private val loginDto = LoginRequestDto("youngjun108059@gmail.com", "1234")
        val loginDtoJson = objectMapper.writeValueAsString(loginDto)!!
        val loginResponseDto = LoginResponseDto(
            id = 1,
            name = "YoungJun",
            grantType = "Bearer",
            accessToken = "test123token",
            accessTokenExpiresIn = 1673557504848,
        )
    }
}
