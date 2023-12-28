package com.example.shemajamebelin6.presentation.passcode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PassCodeViewModel: ViewModel() {
    private val _inputStateFlow = MutableStateFlow<PassCodeResource>(PassCodeResource.ChangeInput(""))
    val inputStateFlow = _inputStateFlow.asStateFlow()

    fun onEvent(event: PassCodeEvent) {
        when(event) {
            is PassCodeEvent.AddNumber -> addNumber(number = event.number)
            is PassCodeEvent.RemoveNumber -> removeInput()
        }
    }
    private fun addNumber(number: String) {
        if (_inputStateFlow.value.input.length < 3) {
            _inputStateFlow.value = PassCodeResource.ChangeInput(_inputStateFlow.value.input + number)
        } else if (_inputStateFlow.value.input.length == 3) {
            _inputStateFlow.value = PassCodeResource.ChangeInput(_inputStateFlow.value.input + number)
            checkPasscode()
        }
    }

    private fun checkPasscode() {
        if (_inputStateFlow.value.input == "0934") {
            _inputStateFlow.value = PassCodeResource.Success
        } else {
            _inputStateFlow.value = PassCodeResource.Failure
        }
    }

    private fun removeInput() {
        if (_inputStateFlow.value.input.isNotEmpty()) {
            _inputStateFlow.value = PassCodeResource.ChangeInput(_inputStateFlow.value.input.dropLast(1))

        }
    }
}

