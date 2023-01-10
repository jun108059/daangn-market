package me.youngjun.daangnmarket.infra.aws

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Service
class AmazonS3ResourceStorage(
    val amazonS3Client: AmazonS3Client
) {
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null

    fun store(
        multipartFile: MultipartFile
    ): String {
        val metadata = ObjectMetadata()
        metadata.contentType = multipartFile.contentType
        metadata.contentLength = multipartFile.size
        amazonS3Client.putObject(
            PutObjectRequest(
                bucket,
                multipartFile.name,
                multipartFile.inputStream,
                metadata
            )
        )
        return amazonS3Client.getUrl(bucket, multipartFile.name).toString()
    }
}