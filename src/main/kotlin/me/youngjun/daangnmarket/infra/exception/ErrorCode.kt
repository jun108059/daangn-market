package me.youngjun.daangnmarket.infra.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // 400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 값을 확인해주세요."),

    // 404 NOT_FOUND 잘못된 리소스 접근
    DEFAULT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 정보입니다."),
    AREA_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 지역입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),

    // 409 CONFLICT 중복된 리소스
    DUPLICATE_USER_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버팀에 연락주세요.");

}