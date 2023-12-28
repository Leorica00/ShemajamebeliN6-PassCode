package com.example.shemajamebelin6.presentation.passcode

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.shemajamebelin6.BaseFragment
import com.example.shemajamebelin6.R
import com.example.shemajamebelin6.databinding.FragmentPassCodeBinding
import kotlinx.coroutines.launch

class PassCodeFragment : BaseFragment<FragmentPassCodeBinding>(FragmentPassCodeBinding::inflate) {

    private val inputPasscode = StringBuilder()
    private lateinit var passCodeDots: Array<View>
    private val passCodeViewModel: PassCodeViewModel by viewModels()

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                passCodeViewModel.inputStateFlow.collect {
                    handlePassCodeResource(it)
                }
            }
        }
    }

    override fun setUp() {
        passCodeDots = with(binding) {
            arrayOf(viewCode1, viewCode2, viewCode3, viewCode4)
        }

    }

    override fun setUpListeners() {
        numbersClickListener()
        numberDeleteListener()
    }

    private fun numbersClickListener() {
        with(binding) {
            btnNumber0.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("0"))
            }
            btnNumber1.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("1"))
            }
            btnNumber2.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("2"))
            }
            btnNumber3.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("3"))
            }
            btnNumber4.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("4"))
            }
            btnNumber5.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("5"))
            }
            btnNumber6.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("6"))
            }
            btnNumber7.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("7"))
            }
            btnNumber8.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("8"))
            }
            btnNumber9.setOnClickListener {
                passCodeViewModel.onEvent(PassCodeEvent.AddNumber("9"))
            }

        }
    }

    private fun handlePassCodeResource(resource: PassCodeResource) {
        when(resource) {
            is PassCodeResource.ChangeInput -> {
                if(passCodeViewModel.inputStateFlow.value.input.isNotEmpty()){
                    passCodeDots[passCodeViewModel.inputStateFlow.value.input.length - 1].setBackgroundResource(R.drawable.costume_colored_dot)
                    passCodeDots.forEach { it.setBackgroundResource(R.drawable.costume_starting_dot) }
                    for (i in 0..<resource.text.length) {
                        d("browhatthefuck", i.toString())
                        passCodeDots[i].setBackgroundResource(R.drawable.costume_colored_dot)
                    }
                }
            }
            is PassCodeResource.Success -> {
                Toast.makeText(requireContext(), "Congrats", Toast.LENGTH_SHORT).show()
                findNavController().navigate(PassCodeFragmentDirections.actionPassCodeFragmentToLoginFragment())
            }
            is PassCodeResource.Failure -> {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                resetInputDots()
            }
            is PassCodeResource.Empty -> {}
        }
    }

    private fun numberDeleteListener() {
        binding.btnBackspace.setOnClickListener {
            passCodeViewModel.onEvent(PassCodeEvent.RemoveNumber)
        }
    }

    private fun resetInputDots() {
        inputPasscode.clear()
        passCodeDots.forEach { it.setBackgroundResource(R.drawable.costume_starting_dot) }
    }
}