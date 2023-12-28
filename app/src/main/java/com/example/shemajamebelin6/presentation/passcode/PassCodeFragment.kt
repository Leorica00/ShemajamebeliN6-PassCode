package com.example.shemajamebelin6.presentation.passcode

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shemajamebelin6.BaseFragment
import com.example.shemajamebelin6.R
import com.example.shemajamebelin6.databinding.FragmentPassCodeBinding
import kotlinx.coroutines.launch

class PassCodeFragment : BaseFragment<FragmentPassCodeBinding>(FragmentPassCodeBinding::inflate) {

    private lateinit var passCodeDots: Array<View>
    private val passCodeViewModel: PassCodeViewModel by viewModels()
    private val passCodeAdapter = PassCodeRecyclerViewAdapter()

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

        with(binding.recyclerPassCode) {
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            adapter = passCodeAdapter.apply {
                submitList(buttons)
            }

        }
    }

    override fun setUpListeners() {
        numbersClickListener()
        numberDeleteListener()
    }

    private fun numbersClickListener() {
        passCodeAdapter.onNumberItemClick = {
            passCodeViewModel.onEvent(PassCodeEvent.AddNumber(it.number))
        }

    }

    private fun handlePassCodeResource(resource: PassCodeResource) {
        when (resource) {
            is PassCodeResource.ChangeInput -> {
                if (passCodeViewModel.inputStateFlow.value.input.isNotEmpty()) {
                    passCodeDots[passCodeViewModel.inputStateFlow.value.input.length - 1].setBackgroundResource(
                        R.drawable.costume_colored_dot
                    )
                    passCodeDots.forEach { it.setBackgroundResource(R.drawable.costume_starting_dot) }
                    for (i in 0..<resource.text.length) {
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
        }
    }

    private fun numberDeleteListener() {
        passCodeAdapter.onDeleteItemClick =
            { passCodeViewModel.onEvent(PassCodeEvent.RemoveNumber) }
    }

    private fun resetInputDots() {
        passCodeDots.forEach { it.setBackgroundResource(R.drawable.costume_starting_dot) }
    }

    private val buttons = listOf(
        PassCodeButton(1, "1"),
        PassCodeButton(2, "2"),
        PassCodeButton(3, "3"),
        PassCodeButton(4, "4"),
        PassCodeButton(5, "5"),
        PassCodeButton(6, "6"),
        PassCodeButton(7, "7"),
        PassCodeButton(8, "8"),
        PassCodeButton(9, "9"),
        PassCodeButton(10, "", R.drawable.ic_fingerprint),
        PassCodeButton(11, "0"),
        PassCodeButton(12, "", R.drawable.ic_backspace),
    )
}