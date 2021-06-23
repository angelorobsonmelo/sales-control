package com.angelorobson.product.presentation.fragments.products.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.databinding.FragmentProductsBinding
import com.angelorobson.product.presentation.adapters.ProductsAdapter
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModel()
    private val productAdapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts()

        setupRecyclerView()
        initObserver()
        binding.productsFloatingActionButton.setOnClickListener {
            val action = ProductsFragmentDirections.actionProductsFragmentToAddProductFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        binding.productsRecyclerView.run {
            adapter = productAdapter
        }
    }

    private fun initObserver() {
        viewModel.productsFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it) {
                is CallbackResult.Error -> {
                    Log.d("CallbackResult", "Error")
                }
                is CallbackResult.Loading -> {
                    Log.d("CallbackResult", "Loading")
                }
                is CallbackResult.Success -> {
                    productAdapter.submitList(it.data)
                    Log.d("CallbackResult", "Success")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}