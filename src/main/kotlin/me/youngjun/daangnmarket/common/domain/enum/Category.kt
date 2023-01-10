package me.youngjun.daangnmarket.common.domain.enum

enum class Category(
    val categoryCode: String,
    val categoryName: String
) {
    DIGITAL_DEVICE("1", "디지털기기"),
    HOME_APPLIANCES("2", "생활가전"),
    FURNITURE_INTERIOR("3", "가구/인테리어"),
    CHILD("4", "유아동"),
    LIFE_PROCESSED_FOOD("5", "생활/가공식품"),
    CHILDREN_BOOK("6", "유아도서"),
    SPORTS("7", "스포츠/레저"),
    WOMEN_ACCESSORY("8", "여성잡화"),
    WOMEN_CLOTHING("9", "여성의류"),
    MEN_FASHION("10", "남성패션/잡화"),
    GAME_HOBBY("11", "게임/취미"),
    BEAUTY("12", "뷰티/미용"),
    PET_SUPPLIES("13", "반려동물용품"),
    BOOK_TICKET_ALBUM("14", "도서/티켓/음반"),
    PLANT("15", "식물"),
    OTHER_USED_GOODS("16", "기타 중고물품"),
    USED_CAR("17", "중고차");
}
