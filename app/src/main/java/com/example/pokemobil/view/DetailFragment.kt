package com.example.pokemobil.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.pokemobil.databinding.FragmentDetailBinding
import com.example.pokemobil.model.Status
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
                        binding.tvHeight.text = data.height.toString()
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), "There was an error", Toast.LENGTH_LONG)
                        .show()
                }

                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Yükleniyor", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}