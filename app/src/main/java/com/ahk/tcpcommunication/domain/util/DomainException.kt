package com.ahk.tcpcommunication.domain.util

import com.ahk.tcpcommunication.core.model.ErrorModel

sealed class DomainException(errorModel: ErrorModel) : Exception() {
    class InvalidLoginArguments(errorModel: ErrorModel) : DomainException(errorModel)
}
