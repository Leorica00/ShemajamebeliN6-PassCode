package com.example.shemajamebelin6.presentation.passcode

sealed class PassCodeEvent {
    data class AddNumber(val number: String): PassCodeEvent()
    data object RemoveNumber: PassCodeEvent()
}