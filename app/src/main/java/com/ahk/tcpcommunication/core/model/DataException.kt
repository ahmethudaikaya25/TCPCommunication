package com.ahk.tcpcommunication.core.model

sealed class DataException(
    val errorModel: ErrorModel,
) : Exception() {
    class ConnectionError(errorModel: ErrorModel) : DataException(errorModel)
}
