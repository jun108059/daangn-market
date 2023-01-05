package me.youngjun.daangnmarket.infra.exception

class DuplicationMemberException(
    val errorCode: ErrorCode
): IllegalStateException(errorCode.message)