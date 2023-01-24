package me.youngjun.daangnmarket.api.image.service

import me.youngjun.daangnmarket.common.domain.Image
import me.youngjun.daangnmarket.common.repository.ImageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Transactional
    fun updateImages(
        productId: Long,
        imageList: List<Image>
    ) {
        // delete 기존 이미지
        val oldImageList = imageRepository.findByProductId(productId)
        imageRepository.deleteAll(oldImageList)
        // insert 새로운 이미지
        imageRepository.saveAll(imageList)
    }
}