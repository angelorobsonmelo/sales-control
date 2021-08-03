package com.angelorobson.product.presentation.fragments.products.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.databinding.FragmentEditProductBinding
import com.angelorobson.product.presentation.viewmodel.EditProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


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
    }

    private fun initGetProductObserver() {
        viewModel.productFlow.asLiveData().observe(viewLifecycleOwner, {
            when (it) {
                is CallbackResult.Error -> {

                }
                is CallbackResult.Loading -> {

                }
                is CallbackResult.Success -> {
                    binding.addFragment.product = it.data
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}