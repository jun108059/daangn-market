package me.youngjun.daangnmarket.api.member.controller

import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.api.member.service.MemberService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

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

    @GetMapping("/api/v1/member")
    fun getMember(
        principal: Principal,
    ): ResponseEntity<Any> {
        val memberInfo = memberService.getMemberInfo(principal.name.toLong())
        return ResponseEntity(memberInfo, HttpStatus.OK)
    }

}