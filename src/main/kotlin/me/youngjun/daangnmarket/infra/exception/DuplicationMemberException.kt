package me.youngjun.daangnmarket.infra.exception

class DuplicationMemberException(
    errorCode: ErrorCode
): IllegalStateException(errorCode.message)