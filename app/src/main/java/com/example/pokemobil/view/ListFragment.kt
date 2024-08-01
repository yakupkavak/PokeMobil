package com.example.pokemobil.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.pokemobil.R
import com.example.pokemobil.adapter.ListAdapter
import com.example.pokemobil.databinding.FragmentListBinding
import com.example.pokemobil.model.Status
import com.example.pokemobil.util.navigate
import com.example.pokemobil.util.observe
import com.example.pokemobil.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListAdapter
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListAdapter().also { adapter ->
            adapter.onItemClick = {
                setOnClick(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
    }

    private fun setObserve() {
        viewModel.getList()
        observe(viewModel.pokemonList) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.lottieAnimation.cancelAnimation()
                    binding.lottieAnimation.isVisible = false
                    binding.rvList.isVisible = true
                    it.data?.let { data ->
                        val nameList = data.results.map { pokemonResult -> pokemonResult.name }
                        adapter.submit(nameList)
                        binding.rvList.adapter = adapter
                    }
                }

                Status.ERROR -> {
                    binding.lottieAnimation.setAnimation(R.raw.cat)
                    binding.rvList.isVisible = false
                }

                Status.LOADING -> {
                    binding.lottieAnimation.isVisible = true
                    binding.rvList.isVisible = false
                }
            }
        }
    }

    private fun setOnClick(data: String) {
        navigate(ListFragmentDirections.actionListFragmentToDetailFragment(data))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}