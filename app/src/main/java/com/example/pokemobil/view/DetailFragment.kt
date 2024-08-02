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
import com.example.pokemobil.model.Status
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
        viewModel.getPokemon(pokemonName)
        observe(viewModel.pokemon) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        val animated = data.sprites.versions.generationV.blackWhite.animated
                        with(binding) {
                            changeUrl(animated)
                            fabPokemon.setOnClickListener {
                                changeUrl(animated)
                            }
                            tvPokemonName.text = pokemonName
                            tvHeight.text = data.height.toString()
                            tvExperience.text = data.base_experience.toString()
                            tvHeart.text = data.stats[0].base_stat.toString()
                            tvSword.text = data.stats[1].base_stat.toString()
                            tvGuard.text = data.stats[2].base_stat.toString()
                            tvSpecialAttack.text = data.stats[3].base_stat.toString()
                            tvSpecialDefence.text = data.stats[4].base_stat.toString()
                            tvSpeed.text = data.stats[5].base_stat.toString()
                        }
                    }
                }
                Status.ERROR -> {
                }

                Status.LOADING -> {
                }
            }
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