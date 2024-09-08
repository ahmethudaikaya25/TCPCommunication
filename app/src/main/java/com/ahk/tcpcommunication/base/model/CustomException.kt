package com.ahk.tcpcommunication.base.model

abstract class CustomException(
    val errorModel: ErrorModel,
) : Exception() {
    companion object {
        fun getDefaultCustomException(): CustomException {
            return DefaultCustomException()
        }
    }
}
