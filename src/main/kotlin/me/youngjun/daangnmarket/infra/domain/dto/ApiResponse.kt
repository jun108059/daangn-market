package me.youngjun.daangnmarket.infra.domain.dto

import me.youngjun.daangnmarket.infra.exception.ErrorCode
import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val status: HttpStatus?,
    val message: String?,
    val data: T?
) {
    fun <T> success(
        data: T
    ): ApiResponse<T> {
        return ApiResponse(
            status = HttpStatus.OK,
            message = "",
            data = data)
    }

    fun <T> error(
        errorCode: ErrorCode
    ): ApiResponse<T> {
        return ApiResponse(
            status = errorCode.status,
            message = errorCode.message,
            data = null
        )
    }
}