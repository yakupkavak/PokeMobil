package com.example.pokemobil.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.pokemobil.databinding.FragmentDetailBinding
import com.example.pokemobil.model.Animated
import com.example.pokemobil.model.DetailPokemonModel
import com.example.pokemobil.util.getUrl
import com.example.pokemobil.util.observe
import com.example.pokemobil.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var pokemonName: String
    private val viewModel: DetailViewModel by viewModels()
    private var changePokemon = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonName = args.pokemonName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
    }

    private fun setObserve() {

        viewModel.getPokemon({ detailPokemon -> setOnSuccess(detailPokemon) }, pokemonName)

        observe(viewModel.success) {

        }
        observe(viewModel.loading) {
            println("loading")
        }
        observe(viewModel.error) {
            println("error")
        }
    }

    private fun setOnSuccess(dpMdel: DetailPokemonModel) {
        with(binding) {
            changeUrl(dpMdel.animated)
            fabPokemon.setOnClickListener {
                changeUrl(dpMdel.animated)
            }
            tvPokemonName.text = pokemonName
            tvHeight.text = dpMdel.height
            tvExperience.text = dpMdel.exp
            tvHeart.text = dpMdel.heart
            tvSword.text = dpMdel.sword
            tvGuard.text = dpMdel.guard
            tvSpecialAttack.text = dpMdel.specialAttack
            tvSpecialDefence.text = dpMdel.specialDefence
            tvSpeed.text = dpMdel.speed
        }
    }

    private fun changeUrl(urlData: Animated) {
        if (changePokemon) {
            binding.ivPokemon.getUrl(urlData.front_default)
            changePokemon = false
        } else {
            binding.ivPokemon.getUrl(urlData.back_default)
            changePokemon = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}