package me.youngjun.daangnmarket.infra.exception

open class BaseException(
    open val errorCode: ErrorCode
) : RuntimeException()