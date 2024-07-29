package com.example.pokemobil.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
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
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { data ->
                        val nameList = data.results.map { pokemonResult -> pokemonResult.name }
                        adapter.submit(nameList)
                        binding.rvList.adapter = adapter
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "There was an error", Toast.LENGTH_LONG)
                        .show()
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setOnClick(data: String) {
        navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}