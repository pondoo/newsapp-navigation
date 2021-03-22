package com.pondoo.application.model

data class ResultFlow<out T>(val status: Status, val data: T?, val error: Error?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): ResultFlow<T> {
            return ResultFlow(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Error?): ResultFlow<T> {
            return ResultFlow(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): ResultFlow<T> {
            return ResultFlow(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "ResultFlow(status=$status, data=$data, error=$error, message=$message)"
    }
}