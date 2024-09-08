package com.ahk.tcpcommunication.base.util

import com.ahk.tcpcommunication.base.model.CustomException

fun Exception.toCustomException(
    exception: Exception,
    errorModel: CustomException?,
): CustomException {
    return if (exception is CustomException) {
        exception
    } else {
        if (errorModel != null) {
            return errorModel
        }
        CustomException.getDefaultCustomException()
    }
}

fun Throwable.toCustomException(
    throwable: Throwable,
    errorModel: CustomException?,
): CustomException {
    return if (throwable is CustomException) {
        throwable
    } else {
        if (errorModel != null) {
            return errorModel
        }
        CustomException.getDefaultCustomException()
    }
}

fun Exception.toCustomException(
    exception: Exception,
): CustomException {
    return if (exception is CustomException) {
        exception
    } else {
        CustomException.getDefaultCustomException()
    }
}

fun Throwable.toCustomException(
    throwable: Throwable,
): CustomException {
    return if (throwable is CustomException) {
        throwable
    } else {
        CustomException.getDefaultCustomException()
    }
}
