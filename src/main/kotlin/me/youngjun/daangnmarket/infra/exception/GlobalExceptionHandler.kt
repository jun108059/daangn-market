package me.youngjun.daangnmarket.infra.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateException::class)
    protected fun handleDuplicationException(
        e: DuplicateException
    ): ResponseEntity<String> {
        return ResponseEntity
            .status(e.errorCode.status)
            .body(e.errorCode.message)
    }

    @ExceptionHandler(NotFoundMemberException::class)
    protected fun handleNotFoundMemberException(
        e: NotFoundMemberException
    ): ResponseEntity<String> {
        return ResponseEntity
            .status(e.errorCode.status)
            .body(e.errorCode.message)
    }
}
