package me.youngjun.daangnmarket.common.domain.enum

enum class CategoryEnum(
    val code: String,
    val detail: String,
) {
    DIGITAL_DEVICE("0", "디지털기기"),
    HOME_APPLIANCES("1", "생활가전"),
    FURNITURE_INTERIOR("2", "가구/인테리어"),
    CHILD("3", "유아동"),
    LIFE_PROCESSED_FOOD("4", "생활/가공식품"),
    CHILDREN_BOOK("5", "유아도서"),
    SPORTS("6", "스포츠/레저"),
    WOMEN_ACCESSORY("7", "여성잡화"),
    WOMEN_CLOTHING("8", "여성의류"),
    MEN_FASHION("9", "남성패션/잡화"),
    GAME_HOBBY("10", "게임/취미"),
    BEAUTY("11", "뷰티/미용"),
    PET_SUPPLIES("12", "반려동물용품"),
    BOOK_TICKET_ALBUM("13", "도서/티켓/음반"),
    PLANT("14", "식물"),
    OTHER_USED_GOODS("15", "기타 중고물품"),
    USED_CAR("16", "중고차"),
    ;

    companion object {
        fun of(
            code: String,
        ): CategoryEnum? = CategoryEnum.values().firstOrNull() {
            it.code == code
        }
    }
}
