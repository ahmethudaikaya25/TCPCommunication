package com.ahk.tcpcommunication.core.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ErrorModel(
    val code: ErrorCode,
    @StringRes val message: Int,
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
)
