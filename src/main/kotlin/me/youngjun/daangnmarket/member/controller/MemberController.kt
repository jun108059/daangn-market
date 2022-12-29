package me.youngjun.daangnmarket.member.controller

import me.youngjun.daangnmarket.member.dto.LoginForm
import me.youngjun.daangnmarket.member.dto.MemberJoinForm
import me.youngjun.daangnmarket.member.service.MemberService
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
            .addObject("member", MemberJoinForm())
    }

    @PostMapping("/join")
    fun join(
        form: MemberJoinForm
    ): ModelAndView {
        val savedMember = memberService.join(form)
        return ModelAndView("/member/join-ok")
            .addObject("email", savedMember.email)
    }
}