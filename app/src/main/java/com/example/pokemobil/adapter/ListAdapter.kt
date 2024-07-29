package com.example.pokemobil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokemobil.databinding.PokemonRowBinding

class ListAdapter : Adapter<ListAdapter.ListViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun submit(items: List<String>) {
        asyncListDiffer.submitList(items)
    }

    inner class ListViewHolder(private val binding: PokemonRowBinding) : ViewHolder(binding.root) {
        fun bind(data: String) {
            with(binding) {
                root.setOnClickListener {
                    onItemClick?.invoke(data)
                }
                tvRowPokemonName.text = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            PokemonRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }
}