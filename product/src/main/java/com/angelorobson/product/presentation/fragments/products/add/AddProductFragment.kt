package com.angelorobson.product.presentation.fragments.products.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.angelorobson.core.utils.MoneyTextWatcher
import com.angelorobson.product.databinding.FragmentAddProductBinding
import com.angelorobson.product.presentation.model.ProductToSavePresentation
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import java.util.*


class AddProductFragment : Fragment() {


    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var product: ProductToSavePresentation


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = ProductToSavePresentation()
        binding.product = product

        binding.addProductPriceEditText.addTextChangedListener(
            MoneyTextWatcher(
                binding.addProductPriceEditText,
                Locale("pt", "BR")
            ) { valueDouble, _ ->
                product.price = valueDouble
            }
        )

        binding.addProductBarCodeTextInputLayout.setEndIconOnClickListener {
            IntentIntegrator.forSupportFragment(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setBeepEnabled(true)
                .initiateScan()
        }

        binding.addProductSaveButton.setOnClickListener {
            print(binding.product)
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
                binding.addProductBarCodeEditText.setText(result.contents)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}