package com.example.pokemobil.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
    private var pokemonId: Int = 0
    private val viewModel: DetailViewModel by viewModels()
    private var changePokemon = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonId = args.pokemonId
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
        setOnClick()
    }

    private fun setObserve() {

        viewModel.getPokemon(pokemonId)

        observe(viewModel.success) {
            setOnSuccess(it)
        }
        observe(viewModel.loading) {
        }
        observe(viewModel.error) {
        }
    }

    private fun setOnSuccess(dpModel: DetailPokemonModel) {
        pokemonId = dpModel.pokemonId
        with(binding) {
            if (pokemonId == 1) {
                btnBack.isVisible = false
            }
            else if (pokemonId == 200) {
                btnNext.isVisible = false
            }
            else {
                btnBack.isVisible = true
                btnNext.isVisible = true
            }
            println(pokemonId)
            ivPokemon.getUrl(dpModel.animated.frontDefault)
            fabPokemon.setOnClickListener {
                changeUrl(dpModel.animated)
            }
            tvPokemonName.text = dpModel.pokemonName
            tvHeight.text = dpModel.height
            tvExperience.text = dpModel.exp
            tvHeart.text = dpModel.heart
            tvSword.text = dpModel.sword
            tvGuard.text = dpModel.guard
            tvSpecialAttack.text = dpModel.specialAttack
            tvSpecialDefence.text = dpModel.specialDefence
            tvSpeed.text = dpModel.speed
        }
    }

    private fun setOnClick() {
        with(binding) {
            btnNext.setOnClickListener {
                viewModel.getPokemon(pokemonId + 1)
            }
            btnBack.setOnClickListener {
                viewModel.getPokemon(pokemonId - 1)
            }
        }
    }

    private fun changeUrl(urlData: Animated) {
        if (changePokemon) {
            binding.ivPokemon.getUrl(urlData.backDefault)
            changePokemon = false
        } else {
            binding.ivPokemon.getUrl(urlData.frontDefault)
            changePokemon = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}