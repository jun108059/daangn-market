package me.youngjun.daangnmarket.common.domain.dto

import me.youngjun.daangnmarket.infra.exception.ErrorCode
import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val status: HttpStatus?,
    val message: String?,
    val data: Any?
) {
    companion object {
        fun <T> success(): ApiResponse<T> {
            return ApiResponse(
                status = HttpStatus.OK,
                message = null,
                data = null
            )
        }

        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(
                status = HttpStatus.OK,
                message = null,
                data = data
            )
        }

        fun <T> error(errorCode: ErrorCode): ApiResponse<T> {
            return ApiResponse(
                status = errorCode.status,
                message = errorCode.message,
                data = null
            )
        }
    }
}