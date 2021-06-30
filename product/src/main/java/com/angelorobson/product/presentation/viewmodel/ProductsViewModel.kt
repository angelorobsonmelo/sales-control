package com.angelorobson.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.domain.usecase.GetProductByBarcodeUseCase
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.presentation.mapper.ObjectDomainToPresentationMapper
import com.angelorobson.product.presentation.model.ProductPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val useCase: GetProductsUseCase,
    private val getByNameUseCase: GetProductByNameUseCase,
    private val getByBarcode: GetProductByBarcodeUseCase,
    private val mapperDomain: ObjectDomainToPresentationMapper,
) : ViewModel() {

    private val _productsFlow =
        MutableStateFlow<CallbackResult<List<ProductPresentation>>>(CallbackResult.Loading())
    val productsFlow: StateFlow<CallbackResult<List<ProductPresentation>>> get() = _productsFlow

    fun getProducts() {
        viewModelScope.launch {
            useCase()
                .catch {
                    _productsFlow.value = CallbackResult.Error(it.localizedMessage)
                }
                .collect {
                    val items = it.map { productDomain -> mapperDomain.map(productDomain) }
                    _productsFlow.value = CallbackResult.Success(items)
                }
        }
    }

    fun findByName(term: String) {
        viewModelScope.launch {
            getByNameUseCase(term)
                .catch {
                    _productsFlow.value = CallbackResult.Error(it.localizedMessage)
                }
                .collect {
                    val items = it.map { productDomain -> mapperDomain.map(productDomain) }
                    _productsFlow.value = CallbackResult.Success(items)
                }
        }
    }

    fun findByBarcode(barcode: String) {
        viewModelScope.launch {
            getByBarcode(barcode)
                .catch {
                    _productsFlow.value = CallbackResult.Error(it.localizedMessage)
                }
                .collect {
                    val items = it.map { productDomain -> mapperDomain.map(productDomain) }
                    _productsFlow.value = CallbackResult.Success(items)
                }
        }
    }

}