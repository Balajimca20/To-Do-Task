package com.example.to_do_list.data.network

import androidx.annotation.Keep

@Keep
data class ApiFailure(
    val msg: TextDto,
    val errorCode: String
) : Exception(msg.en) {
    companion object {

        fun parseErrorMessage(msg: TextDto): ApiFailure {
            return ApiFailure(
                msg,
                ""
            )
        }

        fun create(exception: Exception): ApiFailure {
            return if (exception is ApiFailure) {
                exception
            } else ApiFailure(
                TextDto(
                    exception.message.toString(),
                    exception.message.toString()
                ),
                "L0"
            )
        }
    }
}

@Keep
data class TextDto(
    val en: String?,
    val ar: String?
)
