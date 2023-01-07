package me.youngjun.daangnmarket.infra.exception

import me.youngjun.daangnmarket.common.domain.dto.ApiResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DuplicationMemberException::class)
    protected fun handleDuplicationMemberException(
        e: DuplicationMemberException
    ): ApiResponse<Nothing> {
        return ApiResponse(
            status = e.errorCode.status,
            message = e.errorCode.message,
            null
        )
    }

}