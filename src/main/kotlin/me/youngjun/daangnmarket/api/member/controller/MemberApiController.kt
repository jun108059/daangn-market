package me.youngjun.daangnmarket.api.member.controller

import me.youngjun.daangnmarket.api.member.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.api.member.service.MemberService
import me.youngjun.daangnmarket.common.domain.dto.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {

    @PostMapping("/api/v1/member")
    fun createMember(
        @RequestBody memberJoinForm: MemberJoinRequestDto,
    ): ApiResponse<Nothing> {
        memberService.join(memberJoinForm)
        return ApiResponse.success()
    }

    @PostMapping("/api/v1/login")
    fun login(
        @RequestBody loginForm: LoginRequestDto,
    ): ApiResponse<Nothing> {
        memberService.login(loginForm)
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

}