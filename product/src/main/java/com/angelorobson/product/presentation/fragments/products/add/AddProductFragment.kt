package com.angelorobson.product.presentation.fragments.products.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.angelorobson.core.extensions.displaySnackBar
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.core.utils.MoneyTextWatcher
import com.angelorobson.core.utils.resetTextInputErrorsOnTextChanged
import com.angelorobson.product.R
import com.angelorobson.product.databinding.FragmentAddProductBinding
import com.angelorobson.product.presentation.model.ProductToSavePresentation
import com.angelorobson.product.presentation.viewmodel.AddProductViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class AddProductFragment : Fragment() {


    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding()
        setupEditTextPrice()
        setupBarCodeTextInputLayout()
        initListenerFormValidationObserver()
        initSaveResultsObserver()
        initSaveButtonClickListener()
    }

    private fun initSaveButtonClickListener() {
        binding.addProductSaveButton.setOnClickListener {
            binding.addProductSaveButton.isEnabled = false
            binding.product?.apply {
                viewModel.saveProduct(this)
            }
        }
    }

    private fun setupDataBinding() {
        binding.product = ProductToSavePresentation()
    }

    private fun setupBarCodeTextInputLayout() {
        binding.addProductBarCodeTextInputLayout.setEndIconOnClickListener {
            IntentIntegrator.forSupportFragment(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setBeepEnabled(true)
                .initiateScan()
        }
    }

    private fun setupEditTextPrice() {
        binding.addProductPriceEditText.addTextChangedListener(
            MoneyTextWatcher(
                binding.addProductPriceEditText,
                Locale("pt", "BR")
            ) { valueDouble, _ ->
                binding.product?.price = valueDouble
            }
        )
    }

    private fun initSaveResultsObserver() {
        viewModel.saveResultFlow.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is CallbackResult.Error -> {
                    binding.addProductLinearLayout.displaySnackBar(getString(R.string.an_error_has_occurred))
                    enableButton()
                }
                is CallbackResult.Loading -> {
                }
                is CallbackResult.Success -> {
                    enableButton()
                    clearForm()

                    binding.addProductLinearLayout.displaySnackBar(
                        getString(R.string.save_successfully),
                        backgroundColorSnackBar = ContextCompat.getColor(
                            requireContext(),
                            R.color.green_snack_bar_success
                        )
                    )
                }
            }
        }
    }

    private fun clearForm() {
        binding.product = ProductToSavePresentation()
        binding.addProductPriceEditText.setText("0")
    }

    private fun enableButton() {
        binding.addProductSaveButton.isEnabled = true
    }

    private fun initListenerFormValidationObserver() {
        resetTextInputErrorsOnTextChanged(
            binding.addProductNameTextInputLayout,
            binding.addProductDescriptionTextInputLayout,
            binding.addProductPriceTextInputLayout
        )

        viewModel.name.observe(viewLifecycleOwner, {
            binding.addProductNameTextInputLayout.error = it
            enableButton()
        })

        viewModel.description.observe(viewLifecycleOwner, {
            binding.addProductDescriptionTextInputLayout.error = it
            enableButton()
        })

        viewModel.price.observe(viewLifecycleOwner, {
            binding.addProductPriceTextInputLayout.error = it
            enableButton()
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
                binding.addProductBarCodeEditText.setText(result.contents)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}