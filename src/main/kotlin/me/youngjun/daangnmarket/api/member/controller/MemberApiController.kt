package me.youngjun.daangnmarket.api.member.controller

import me.youngjun.daangnmarket.api.member.dto.MemberInfoResponseDto
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.api.member.dto.ProfileChangeRequestDto
import me.youngjun.daangnmarket.api.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {

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
    ): ResponseEntity<MemberInfoResponseDto> {
        val memberInfo = memberService.getMemberInfo(principal.name.toLong())
        return ResponseEntity(memberInfo, HttpStatus.OK)
    }

    @PutMapping("/api/v1/member")
    fun updateMember(
        @RequestBody profileChangeRequestDto: ProfileChangeRequestDto,
        principal: Principal,
    ): ResponseEntity<Any> {
        memberService.changeProfile(principal.name.toLong(), profileChangeRequestDto)
        return ResponseEntity(null, HttpStatus.OK)
    }
}
