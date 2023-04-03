package me.youngjun.daangnmarket.api.image.controller

import me.youngjun.daangnmarket.api.image.service.UploadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UploadApiController(
    private val uploadService: UploadService,
) {
    @PostMapping("api/v1/image")
    fun uploadImage(
        @RequestPart("image") multipartFile: MultipartFile,
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(uploadService.save(multipartFile))
    }
}
