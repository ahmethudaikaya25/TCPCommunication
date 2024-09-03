package com.ahk.tcpcommunication.domain.util

import android.util.Patterns

fun String.isIP(): Boolean = this.matches(Regex(Patterns.IP_ADDRESS.pattern()))

fun Int.isPort(): Boolean = this in 0..65535
