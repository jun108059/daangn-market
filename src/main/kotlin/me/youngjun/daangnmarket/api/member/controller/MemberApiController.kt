package me.youngjun.daangnmarket.api.member.controller

import me.youngjun.daangnmarket.api.member.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.api.member.service.MemberService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    @PostMapping("/api/v1/member")
    fun createMember(
        @RequestBody memberJoinForm: MemberJoinRequestDto,
    ): ResponseEntity<Any> {
        val memberId = memberService.join(memberJoinForm)
        return ResponseEntity(memberId, HttpStatus.CREATED)
    }

    @PostMapping("/api/v1/login")
    fun signIn(
        @RequestBody loginForm: LoginRequestDto,
    ): ResponseEntity<Any> {
        log.info { loginForm.toString() }
        val token = memberService.loginByEmail(loginForm)
        log.info { "Login completed : [${loginForm.email}], $token" }
        return ResponseEntity(token, HttpStatus.OK)
    }
//
//    @GetMapping("/api/v1/member")
//    fun getMemberById(
//        @RequestParam("id") id: Long,
//    ): ApiResponse<Any> {
//        memberService.findMemberById(id)
//    }
//

}