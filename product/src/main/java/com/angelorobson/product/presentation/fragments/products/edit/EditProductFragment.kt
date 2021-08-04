package com.angelorobson.product.presentation.fragments.products.edit

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
import androidx.navigation.fragment.navArgs
import com.angelorobson.core.extensions.displaySnackBar
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.core.utils.MoneyTextWatcher
import com.angelorobson.product.R
import com.angelorobson.product.databinding.FragmentEditProductBinding
import com.angelorobson.product.presentation.viewmodel.EditProductViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class EditProductFragment : Fragment() {

    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    private val args: EditProductFragmentArgs by navArgs()
    private val viewModel: EditProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProduct(args.idProduct)
        initGetProductObserver()
        initSaveButtonClickListener()
        setupBarCodeTextInputLayout()
        setupEditTextPrice()
        initSaveResultsObserver()
    }

    private fun initGetProductObserver() {
        viewModel.productFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it) {
                is CallbackResult.Error -> {
                    binding.addFragment.addProductLinearLayout.displaySnackBar(
                        it.message ?: getString(R.string.an_error_has_occurred)
                    )
                    enableButton()
                }
                is CallbackResult.Loading -> {
                }
                is CallbackResult.Success -> {
                    binding.addFragment.product = it.data
                    binding.addFragment.addProductPriceEditText.setText(it.data?.price.toString())
                }
            }
        })
    }

    private fun initSaveButtonClickListener() {
        binding.addFragment.addProductSaveButton.setOnClickListener {
            binding.addFragment.addProductSaveButton.isEnabled = false
            binding.addFragment.product?.apply {
                viewModel.saveProduct(this)
            }
        }
    }

    private fun setupBarCodeTextInputLayout() {
        binding.addFragment.addProductBarCodeTextInputLayout.setEndIconOnClickListener {
            IntentIntegrator.forSupportFragment(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setBeepEnabled(true)
                .initiateScan()
        }
    }

    private fun setupEditTextPrice() {
        binding.addFragment.addProductPriceEditText.addTextChangedListener(
            MoneyTextWatcher(
                binding.addFragment.addProductPriceEditText,
                Locale("pt", "BR")
            ) { valueDouble, _ ->
                binding.addFragment.product?.price = valueDouble
            }
        )
    }

    private fun initSaveResultsObserver() {
        viewModel.saveResultFlow.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is CallbackResult.Error -> {
                    binding.addFragment.addProductLinearLayout.displaySnackBar(getString(R.string.an_error_has_occurred))
                    enableButton()
                }
                is CallbackResult.Loading -> {
                }
                is CallbackResult.Success -> {
                    enableButton()

                    binding.addFragment.addProductLinearLayout.displaySnackBar(
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                binding.addFragment.addProductBarCodeEditText.setText(result.contents)
            }
        }
    }

    private fun enableButton() {
        binding.addFragment.addProductSaveButton.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}