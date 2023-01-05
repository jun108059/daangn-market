package me.youngjun.daangnmarket.infra.exception

class BaseException(
    val errorCode: ErrorCode
): RuntimeException()