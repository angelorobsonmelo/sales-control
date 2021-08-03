package com.angelorobson.product.presentation.fragments.products.products

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.angelorobson.core.extensions.displaySnackBarWithUndoAction
import com.angelorobson.core.extensions.gone
import com.angelorobson.core.extensions.visible
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.core.utils.SwipeHelper
import com.angelorobson.product.R
import com.angelorobson.product.databinding.FragmentProductsBinding
import com.angelorobson.product.presentation.adapters.ProductsAdapter
import com.angelorobson.product.presentation.model.ProductPresentation
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ProductsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModel()
    private val products = mutableListOf<ProductPresentation?>()

    private val productAdapter = ProductsAdapter {}

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
        recyclerView = binding.productsRecyclerView

        viewModel.getProducts()

        setupRecyclerView()
        setupSwipeItemOptions()
        initObserver()
        initFloatingActionButtonListener()
        initSwipeRefreshListener()
    }

    private fun initSwipeRefreshListener() {
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
        recyclerView.run {
            adapter = productAdapter
        }
    }

    private fun setupSwipeItemOptions() {
        object : SwipeHelper(requireContext(), recyclerView, false) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {
                underlayButtons?.add(createAndHandleDeleteUnderlayButton())
                underlayButtons?.add(createAndHandleEditOverLayButton())
            }
        }
    }

    private fun createAndHandleEditOverLayButton() = SwipeHelper.UnderlayButton(
        getString(R.string.edit),
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_edit),
        Color.parseColor(requireContext().getString(android.R.color.holo_green_light)),
        Color.parseColor(requireContext().getString(android.R.color.white))
    ) { pos: Int ->
         products[pos]?.run {
             val action =
                 ProductsFragmentDirections.actionProductsFragmentToEditProductFragment(
                     this.id,
                     this.name
                 )
             findNavController().navigate(action)
        }

    }

    private fun createAndHandleDeleteUnderlayButton() = SwipeHelper.UnderlayButton(
        getString(R.string.delete),
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_delete),
        Color.parseColor(requireContext().getString(android.R.color.holo_red_light)),
        Color.parseColor(requireContext().getString(android.R.color.white))
    ) { pos: Int ->
        val product = products[pos]

        products.removeAt(pos)
        productAdapter.notifyItemRemoved(pos)

        if (products.isEmpty()) {
            binding.productsNoDataFoundTextView.visible()
        }

        binding.productsConstraintLayout.displaySnackBarWithUndoAction(
            undoClicked = {
                products.add(pos, product)
                productAdapter.notifyItemInserted(pos)

                binding.productsNoDataFoundTextView.gone()
            },
            dismissTimeoutCallback = {

            })
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
                    it.data?.let { items ->
                        products.clear()
                        products.addAll(items)

                        recyclerView.recycledViewPool.clear()
                        productAdapter.notifyDataSetChanged()

                        productAdapter.submitList(products)
                        binding.productsNoDataFoundTextView.gone()
                    }

                    if (products.isEmpty()) {
                        binding.productsNoDataFoundTextView.visible()
                    }

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