package com.example.shemajamebelin6.presentation.passcode

sealed class PassCodeResource(var input: String = "") {
    data class ChangeInput(val text: String): PassCodeResource(input = text)
    data object Success: PassCodeResource()
    data object Failure: PassCodeResource()
}