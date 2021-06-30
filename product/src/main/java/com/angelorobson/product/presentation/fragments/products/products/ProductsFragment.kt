package com.angelorobson.product.presentation.fragments.products.products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.angelorobson.core.extensions.gone
import com.angelorobson.core.extensions.visible
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.R
import com.angelorobson.product.databinding.FragmentProductsBinding
import com.angelorobson.product.presentation.adapters.ProductsAdapter
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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
        initFloatingActionButtonListener()

        binding.productsSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getProducts()
            binding.productsSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initFloatingActionButtonListener() {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_search_by_barcode_action -> {
                IntentIntegrator.forSupportFragment(this)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    .setBeepEnabled(true)
                    .initiateScan()
            }
        }

        return super.onOptionsItemSelected(item)
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
                    binding.productsNoDataFoundTextView.gone()
                }
                is CallbackResult.Loading -> {
                    binding.productsNoDataFoundTextView.gone()
                }
                is CallbackResult.Success -> {
                    productAdapter.submitList(it.data)

                    if (it.data?.isEmpty() == true) {
                        binding.productsNoDataFoundTextView.visible()
                        return@observe
                    }

                    binding.productsNoDataFoundTextView.gone()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                viewModel.findByBarcode(result.contents)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}