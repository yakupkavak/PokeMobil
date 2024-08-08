package com.example.pokemobil.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.pokemobil.adapter.ListAdapter
import com.example.pokemobil.databinding.FragmentListBinding
import com.example.pokemobil.model.PokemonNameUrl
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
    private var pokemonList: List<PokemonNameUrl> = listOf()

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
        setOnRefresh()
    }

    private fun setObserve() {
        onLoading()
        observe(viewModel.success) { dataList ->
            pokemonList = dataList
            onSuccess(pokemonList)
        }
        observe(viewModel.loading) {
            onLoading()
        }
        observe(viewModel.error) {
            onError()
        }
    }

    private fun onSuccess(dataList: List<PokemonNameUrl>) {
        with(binding) {
            lottieAnimation.cancelAnimation()
            lottieAnimation.isVisible = false
            rvList.isVisible = true
            swiperefresh.isRefreshing = false
            updateList(dataList)
        }
    }

    private fun updateList(dataList: List<PokemonNameUrl>) {
        adapter.submit(dataList)
        binding.rvList.adapter = adapter
    }

    private fun onError() {
        viewModel.getList()
        binding.rvList.isVisible = false
    }

    private fun onLoading() {
        binding.lottieAnimation.isVisible = true
        binding.rvList.isVisible = false
    }

    private fun setOnRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            viewModel.getList()
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val filterList = pokemonList.filter { list ->
                        list.pokemonName.contains(query, ignoreCase = true)
                    }
                    updateList(filterList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setOnClick(data: Int) {
        navigate(ListFragmentDirections.actionListFragmentToDetailFragment(data))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}