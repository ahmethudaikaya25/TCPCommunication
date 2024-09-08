package com.ahk.tcpcommunication.base.model

import com.ahk.tcpcommunication.R

class DefaultCustomException : CustomException(
    errorModel = errorModel,
) {
    companion object {
        val errorModel = ErrorModel(
            code = ErrorCode.UNKNOWN_ERROR,
            message = R.string.Unknown_Error,
            description = R.string.Unknown_Error_Message,
            icon = R.drawable.default_error_icon,
        )
    }
}
