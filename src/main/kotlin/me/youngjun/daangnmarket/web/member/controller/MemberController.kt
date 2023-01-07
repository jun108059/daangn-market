package me.youngjun.daangnmarket.web.member.controller

import me.youngjun.daangnmarket.api.member.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.api.member.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class MemberController(
    private val memberService: MemberService,
) {

    @GetMapping("/join")
    fun joinView(
    ): ModelAndView {
        return ModelAndView("/member/join")
            .addObject("member", MemberJoinRequestDto())
    }

//    @PostMapping("/join")
//    fun join(
//        form: MemberJoinRequestDto
//    ): ModelAndView {
//        val savedMember = memberService.join(form)
//        return ModelAndView("/member/join-ok")
//            .addObject("email", savedMember.email)
//    }

    @GetMapping("/login")
    fun login(
    ): ModelAndView {
        return ModelAndView("/member/login")
            .addObject("loginForm", LoginRequestDto())
    }

    @PostMapping("/login")
    fun login(
        form: LoginRequestDto
    ): ModelAndView {
        memberService.login(form)
        return ModelAndView("/product/list")
    }
}