package me.youngjun.daangnmarket.api.member.controller

import me.youngjun.daangnmarket.common.domain.dto.ApiResponse
import me.youngjun.daangnmarket.api.member.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.api.member.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {

    @PostMapping("/api/v1/member")
    fun createMember(
        @RequestParam("memberJoinForm") form: MemberJoinRequestDto,
    ): ApiResponse<Nothing> {
        memberService.join(form)
        return ApiResponse.success()
    }
//
//    @GetMapping("/api/v1/member")
//    fun getMemberById(
//        @RequestParam("id") id: Long,
//    ): ApiResponse<Any> {
//        memberService.findMemberById(id)
//    }
//
//    @PostMapping("/api/v1/login")
//    fun login(
//        @RequestParam("loginForm") form: LoginRequestDto,
//    ): ApiResponse<Nothing> {
//        memberService.login(form)
//        return
//    }
}