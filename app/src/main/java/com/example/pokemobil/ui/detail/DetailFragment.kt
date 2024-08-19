package com.example.pokemobil.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.pokemobil.R
import com.example.pokemobil.databinding.FragmentDetailBinding
import com.example.pokemobil.data.model.Animated
import com.example.pokemobil.data.model.DetailPokemonModel
import com.example.pokemobil.domain.extension.getUrl
import com.example.pokemobil.data.util.ServiceCountConst.MaxServiceCount
import com.example.pokemobil.data.util.ServiceCountConst.MinServiceCount
import com.example.pokemobil.ui.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private var pokemonId: Int = 0
    private val viewModel: DetailViewModel by viewModels()
    private var changePokemon = true

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
            onLoading()
        }
        observe(viewModel.error) {
            Toast.makeText(requireContext(),getString(R.string.error),Toast.LENGTH_LONG).show()
        }
    }
    private fun onLoading(){
        with(binding){
            linearProgress.isIndeterminate = true
            linearProgress.show()
        }
    }

    private fun setOnSuccess(dpModel: DetailPokemonModel) {
        pokemonId = dpModel.pokemonId
        changePokemon = true
        with(binding) {
            linearProgress.isIndeterminate = false
            linearProgress.hide()
            when (pokemonId) {
                MinServiceCount -> {
                    btnBack.isVisible = false
                }
                MaxServiceCount -> {
                    btnNext.isVisible = false
                }
                else -> {
                    btnBack.isVisible = true
                    btnNext.isVisible = true
                }
            }
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