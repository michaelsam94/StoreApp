package com.example.storeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeapp.R
import com.example.storeapp.data.model.Product
import com.example.storeapp.databinding.FragmentHomeBinding
import com.example.storeapp.utils.Resource
import com.example.storeapp.utils.gone
import com.example.storeapp.utils.show
import com.example.storeapp.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(R.layout.fragment_home), ProductsAdapter.Interaction {

    val viewModel: ProductsViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding

    private val productsAdapter by lazy { ProductsAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "YTS App"
        setupRecyclerView()
        observeToProductsLiveData()
    }

    private fun observeToProductsLiveData() {
        viewModel.getProducts().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    binding.progressBar.gone()
                    it.msg?.let { msg -> showToast(msg) }
                }
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    binding.progressBar.gone()
                    if (it.data != null) {
                        productsAdapter.setProduct(it.data)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.productsRecycler.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }



    override fun onItemSelected(position: Int, item: Product) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }
}