package com.example.shemajamebelin6.presentation.passcode

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shemajamebelin6.BaseFragment
import com.example.shemajamebelin6.R
import com.example.shemajamebelin6.databinding.FragmentPassCodeBinding

class PassCodeFragment : BaseFragment<FragmentPassCodeBinding>(FragmentPassCodeBinding::inflate) {

    private val inputPasscode = StringBuilder()
    private lateinit var passCodeDots: Array<View>

    override fun setUpObservers() {
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
                addNumber("0")
            }
            btnNumber1.setOnClickListener {
                addNumber("1")
            }
            btnNumber2.setOnClickListener {
                addNumber("2")
            }
            btnNumber3.setOnClickListener {
                addNumber("3")
            }
            btnNumber4.setOnClickListener {
                addNumber("4")
            }
            btnNumber5.setOnClickListener {
                addNumber("5")
            }
            btnNumber6.setOnClickListener {
                addNumber("6")
            }
            btnNumber7.setOnClickListener {
                addNumber("7")
            }
            btnNumber8.setOnClickListener {
                addNumber("8")
            }
            btnNumber9.setOnClickListener {
                addNumber("9")
            }

        }
    }

    private fun numberDeleteListener() {
        binding.btnBackspace.setOnClickListener {
            if (inputPasscode.isNotEmpty()) {
                inputPasscode.deleteCharAt(inputPasscode.length - 1)
                updateInputDots()
            }
        }
    }

    private fun updateInputDots() {
        passCodeDots.forEach { it.setBackgroundResource(R.drawable.costume_starting_dot) }
        for (i in inputPasscode.indices) {
            passCodeDots[i].setBackgroundResource(R.drawable.costume_colored_dot)
        }
    }

    private fun addNumber(number: String) {
        if (inputPasscode.length < 3) {
            inputPasscode.append(number)
            passCodeDots[inputPasscode.length - 1].setBackgroundResource(R.drawable.costume_colored_dot)
        } else if (inputPasscode.length == 3) {
            inputPasscode.append(number)
            checkPasscode()
        }
    }

    private fun checkPasscode() {
        if (inputPasscode.toString() == "0934") {
            resetInputDots()
            Toast.makeText(requireContext(), "Congrats", Toast.LENGTH_SHORT).show()
            findNavController().navigate(PassCodeFragmentDirections.actionPassCodeFragmentToLoginFragment())
        } else {
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
            resetInputDots()
        }
    }

    private fun resetInputDots() {
        inputPasscode.clear()
        passCodeDots.forEach { it.setBackgroundResource(R.drawable.costume_starting_dot) }
    }
}