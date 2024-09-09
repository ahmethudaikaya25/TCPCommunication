package com.ahk.tcpcommunication.features.login

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.ahk.tcpcommunication.R

@BindingAdapter("uiState", "lastSuccessState", "defaultValue")
fun onTextChanged(
    editText: EditText,
    uiState: LoginUIState,
    lastSuccessState: LoginUIState.Success?,
    defaultValue: String,
) {
    val id: Int = editText.id
    val text: String
    when (id) {
        R.id.name -> {
            text = if (uiState is LoginUIState.Success) {
                uiState.loginUser.name
            } else {
                lastSuccessState?.loginUser?.name ?: defaultValue
            }
        }

        R.id.ip -> {
            text = if (uiState is LoginUIState.Success) {
                uiState.loginUser.ip
            } else {
                lastSuccessState?.loginUser?.ip ?: defaultValue
            }
        }

        R.id.port -> {
            text = if (uiState is LoginUIState.Success) {
                uiState.loginUser.port.toString()
            } else {
                lastSuccessState?.loginUser?.port.toString()
            }
        }

        R.id.password -> {
            text = if (uiState is LoginUIState.Success) {
                uiState.loginUser.password
            } else {
                lastSuccessState?.loginUser?.password ?: defaultValue
            }
        }

        else -> {
            text = defaultValue
        }
    }
    editText.setText(text)
}
