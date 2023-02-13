package me.youngjun.daangnmarket.api.area.service

import me.youngjun.daangnmarket.api.area.dto.AreaView
import me.youngjun.daangnmarket.common.repository.AreaRepository
import org.springframework.stereotype.Service

@Service
class AreaService(
    private val areaRepository: AreaRepository
) {
    fun getAreaList(): List<AreaView> {
        val areaList = areaRepository.findAll()
        return areaList.map {
            AreaView(
                areaCode = it.code!!,
                areaName = it.name!!
            )
        }
    }
}
