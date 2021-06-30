package com.angelorobson.product.presentation.fragments.products.products

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.R
import com.angelorobson.product.databinding.FragmentProductsBinding
import com.angelorobson.product.presentation.adapters.ProductsAdapter
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.products_menu, menu)
        val searchView = setupSearchView(menu)
        setQueryTextListener(searchView)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSearchView(menu: Menu): SearchView {
        val searchItem = menu.findItem(R.id.products_search_action)
        val searchView = searchItem.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.inputType = InputType.TYPE_CLASS_TEXT

        return searchView
    }

    private fun setQueryTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val term = newText.toLowerCase(Locale.ROOT)
                viewModel.findByName(term)
                return false
            }
        })
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