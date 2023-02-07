package me.youngjun.daangnmarket.api.image.service

import me.youngjun.daangnmarket.infra.aws.AmazonS3ResourceStorage
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UploadService(
    private val amazonS3ResourceStorage: AmazonS3ResourceStorage
) {
    fun save(multipartFile: MultipartFile): String {
        return amazonS3ResourceStorage.store(multipartFile)
    }
}
