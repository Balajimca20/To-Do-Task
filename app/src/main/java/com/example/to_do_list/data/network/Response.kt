package com.example.to_do_list.data.network


sealed class Response<T> {
    class Success<T>(val data: T) : Response<T>() {
        inline fun <R> map(transform: (T) -> R): Success<R> {
            return Success(transform(data))
        }
    }

    class Failure<T>(val error: ApiFailure) : Response<T>() {
        inline fun <reified R> switch(): Failure<R> {
            return Failure(error)
        }
    }

    class NoNetwork<T>(val messageId: Int) : Response<T>() {
        inline fun <reified R> switch(): NoNetwork<R> {
            return NoNetwork(messageId)
        }
    }
}
