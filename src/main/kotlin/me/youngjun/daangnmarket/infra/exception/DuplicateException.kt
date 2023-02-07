package me.youngjun.daangnmarket.infra.exception

class DuplicateException(
    override val errorCode: ErrorCode
) : BaseException(errorCode)
