package com.ahk.tcpcommunication.domain.util

import com.ahk.tcpcommunication.base.model.CustomException
import com.ahk.tcpcommunication.base.model.ErrorModel

sealed class DomainException(errorModel: ErrorModel) : CustomException(errorModel) {
    class InvalidLoginArguments(errorModel: ErrorModel) : DomainException(errorModel)
}
