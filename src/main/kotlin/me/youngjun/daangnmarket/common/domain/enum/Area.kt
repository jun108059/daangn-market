package me.youngjun.daangnmarket.common.domain.enum

enum class Area(
    val areaId: Int,
    val areaName: String
) {
    PANGYO(1, "판교"),
    SUJI(2, "수지"),
    JAMSIL(3, "잠실");


    companion object {
        fun of(
            areaId: Int
        ): Area? = values().firstOrNull() {
            it.areaId == areaId
        }
    }

}
