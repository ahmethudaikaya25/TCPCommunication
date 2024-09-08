package com.ahk.tcpcommunication.core.model

import com.ahk.tcpcommunication.base.model.CustomException
import com.ahk.tcpcommunication.base.model.ErrorModel

sealed class DataException(errorModel: ErrorModel) : CustomException(errorModel) {
    class ConnectionError(errorModel: ErrorModel) : DataException(errorModel)
}
