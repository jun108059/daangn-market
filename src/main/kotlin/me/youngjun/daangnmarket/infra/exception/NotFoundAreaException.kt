package me.youngjun.daangnmarket.infra.exception

class NotFoundAreaException(
    override val errorCode: ErrorCode
) : BaseException(errorCode)