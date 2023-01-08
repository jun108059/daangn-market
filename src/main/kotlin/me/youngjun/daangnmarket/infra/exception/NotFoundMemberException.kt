package me.youngjun.daangnmarket.infra.exception

class NotFoundMemberException(
    override val errorCode: ErrorCode
) : BaseException(errorCode)