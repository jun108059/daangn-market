package me.youngjun.daangnmarket.api.sign.controller

import me.youngjun.daangnmarket.api.sign.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.sign.dto.LoginResponseDto
import me.youngjun.daangnmarket.api.sign.service.SignService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SignApiController(
    private val signService: SignService,
) {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    @PostMapping("/api/v1/login")
    fun signIn(
        @RequestBody loginForm: LoginRequestDto,
    ): ResponseEntity<LoginResponseDto> {
        log.info { loginForm.toString() }
        val loginResponseDto = signService.loginByEmail(loginForm)
        log.info { "Login completed : [${loginForm.email}], $loginResponseDto" }
        return ResponseEntity(loginResponseDto, HttpStatus.OK)
    }
}
