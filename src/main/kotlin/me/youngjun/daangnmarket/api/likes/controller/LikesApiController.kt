package me.youngjun.daangnmarket.api.likes.controller

import me.youngjun.daangnmarket.api.likes.dto.LikesRequestDto
import me.youngjun.daangnmarket.api.likes.service.LikesService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class LikesApiController(
    private val likesService: LikesService
) {
    @PostMapping("/api/v1/likes")
    fun addLikesCount(
        @RequestBody likesRequestDto: LikesRequestDto,
        principal: Principal
    ): ResponseEntity<Any> {
        val addLikesId = likesService.addLikes(
            memberId = principal.name.toLong(),
            productId = likesRequestDto.productId
        )
        return ResponseEntity(addLikesId, HttpStatus.CREATED)
    }
}