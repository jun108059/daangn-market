package me.youngjun.daangnmarket.infra.domain.enum

enum class Category(
    val categoryName: String
) {
    DIGITAL_DEVICE("디지털기기"),
    HOME_APPLIANCES("생활가전"),
    FURNITURE_INTERIOR("가구/인테리어"),
    CHILD("유아동"),
    LIFE_PROCESSED_FOOD("생활/가공식품"),
    CHILDREN_BOOK("유아도서"),
    SPORTS("스포츠/레저"),
    WOMEN_ACCESSORY("여성잡화"),
    WOMEN_CLOTHING("여성의류"),
    MEN_FASHION("남성패션/잡화"),
    GAME_HOBBY("게임/취미"),
    BEAUTY("뷰티/미용"),
    PET_SUPPLIES("반려동물용품"),
    BOOK_TICKET_ALBUM("도서/티켓/음반"),
    PLANT("식물"),
    OTHER_USED_GOODS("기타 중고물품"),
    USED_CAR("중고차");
}
