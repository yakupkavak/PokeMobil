package com.example.pokemobil.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokemobil.databinding.PokemonRowBinding
import com.example.pokemobil.data.model.PokemonNameUrl
import com.example.pokemobil.domain.extension.getPokemonId

class ListAdapter : Adapter<ListAdapter.ListViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<PokemonNameUrl>() {
        override fun areItemsTheSame(oldItem: PokemonNameUrl, newItem: PokemonNameUrl): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PokemonNameUrl, newItem: PokemonNameUrl): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun submit(items: List<PokemonNameUrl>) {
        asyncListDiffer.submitList(items)
    }

    inner class ListViewHolder(private val binding: PokemonRowBinding) : ViewHolder(binding.root) {
        fun bind(data: PokemonNameUrl) {
            with(binding) {
                root.setOnClickListener {
                    onItemClick?.invoke(getPokemonId(data.pokemonUrl))
                }
                tvRowPokemonName.text = data.pokemonName
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