package com.example.shemajamebelin6.presentation.passcode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shemajamebelin6.R
import com.example.shemajamebelin6.databinding.ImageButtonRecyclerItemBinding
import com.example.shemajamebelin6.databinding.NumberButtonRecyclerItemBinding


class PassCodeRecyclerViewAdapter : ListAdapter<PassCodeButton, ViewHolder>(MyItemDiffCallback()) {

    companion object {
        const val NUMBER_BUTTON = 1
        const val IMAGE_BUTTON = 2
    }

    var onNumberItemClick: ((PassCodeButton) -> Unit)? = null
    var onDeleteItemClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            NUMBER_BUTTON -> NumberButtonViewHolder(
                NumberButtonRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            IMAGE_BUTTON -> ImageButtonViewHolder(
                ImageButtonRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> NumberButtonViewHolder(
                NumberButtonRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is NumberButtonViewHolder -> holder.bind()
            is ImageButtonViewHolder -> holder.bind()
        }
    }

    inner class NumberButtonViewHolder(private val binding: NumberButtonRecyclerItemBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            binding.btnNumberInput.text = currentList[adapterPosition].number
            onItemClick()
        }

        private fun onItemClick() {
            binding.btnNumberInput.setOnClickListener {
                onNumberItemClick?.invoke(currentList[adapterPosition])
            }
        }
    }

    inner class ImageButtonViewHolder(private val binding: ImageButtonRecyclerItemBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            binding.imgBtnPasscode.setImageResource(currentList[adapterPosition].icon)
            onItemClick()
        }

        private fun onItemClick() {
            binding.imgBtnPasscode.setOnClickListener {
                if (currentList[adapterPosition].icon == R.drawable.ic_backspace)
                    onDeleteItemClick?.invoke()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].number == "") {
            IMAGE_BUTTON
        } else {
            NUMBER_BUTTON
        }
    }


    private class MyItemDiffCallback : DiffUtil.ItemCallback<PassCodeButton>() {

        override fun areItemsTheSame(oldItem: PassCodeButton, newItem: PassCodeButton): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PassCodeButton, newItem: PassCodeButton): Boolean {
            return oldItem == newItem
        }
    }
}


