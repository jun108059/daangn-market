package me.youngjun.daangnmarket.api.area.controller

import me.youngjun.daangnmarket.api.area.dto.AreaView
import me.youngjun.daangnmarket.api.area.service.AreaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AreaApiController(
    private val areaService: AreaService
) {
    @GetMapping("/api/v1/area/list")
    fun getAreaListView(): ResponseEntity<List<AreaView>> {
        val areaList = areaService.getAreaList()
        return ResponseEntity.ok(areaList)
    }
}
