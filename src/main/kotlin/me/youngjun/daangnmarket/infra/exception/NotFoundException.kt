package me.youngjun.daangnmarket.infra.exception

class NotFoundException(
    override val errorCode: ErrorCode
) : BaseException(errorCode)
