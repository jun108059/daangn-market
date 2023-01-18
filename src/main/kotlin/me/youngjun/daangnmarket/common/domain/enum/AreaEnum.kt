package me.youngjun.daangnmarket.common.domain.enum

enum class AreaEnum(
    val areaCode: String,
    val areaName: String
) {
    PANGYO("0001", "판교"),
    SUJI("0002", "수지"),
    JAMSIL("0003", "잠실");

    companion object {
        fun of(
            areaCode: String
        ): AreaEnum? = values().firstOrNull() {
            it.areaCode == areaCode
        }

        fun getName(
            areaCode: String?
        ): String {
            AreaEnum.values().iterator().forEach {
                if (it.areaCode == areaCode) {
                    return it.areaName
                }
            }
            return PANGYO.areaName
        }
    }

}
