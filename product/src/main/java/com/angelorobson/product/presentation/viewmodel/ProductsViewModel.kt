package com.angelorobson.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.domain.usecase.GetProductByBarcodeUseCase
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.InactiveProductUseCase
import com.angelorobson.product.presentation.mapper.ObjectDomainToPresentationMapper
import com.angelorobson.product.presentation.mapper.ObjectPresentationToDomainMapper
import com.angelorobson.product.presentation.model.ProductPresentation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val useCase: GetProductsUseCase,
    private val getByNameUseCase: GetProductByNameUseCase,
    private val inactiveProductUseCase: InactiveProductUseCase,
    private val getByBarcode: GetProductByBarcodeUseCase,
    private val mapperDomain: ObjectDomainToPresentationMapper,
    private val mapperPresentationToDomainMapper: ObjectPresentationToDomainMapper
) : ViewModel() {

    private val _productsFlow =
        MutableStateFlow<CallbackResult<List<ProductPresentation>>>(CallbackResult.Loading())
    val productsFlow: StateFlow<CallbackResult<List<ProductPresentation>>> get() = _productsFlow

    private val _inactiveProductFlow =
        MutableStateFlow<CallbackResult<Void>>(CallbackResult.Loading())
    val inactiveProductFlow: StateFlow<CallbackResult<Void>> get() = _inactiveProductFlow

    fun getProducts() {
        viewModelScope.launch {
            useCase()
                .catch {
                    _productsFlow.value = CallbackResult.Error(it.localizedMessage)
                }
                .map {
                    it.map { productDomain -> mapperDomain.map(productDomain) }
                }
                .collect {
                    _productsFlow.value = CallbackResult.Success(it)
                }
        }
    }

    fun findByName(term: String) {
        viewModelScope.launch {
            getByNameUseCase(term)
                .catch {
                    _productsFlow.value = CallbackResult.Error(it.localizedMessage)
                }
                .map {
                    it.map { productDomain -> mapperDomain.map(productDomain) }
                }
                .collect {
                    _productsFlow.value = CallbackResult.Success(it)
                }
        }
    }

    fun inactiveProduct(productPresentation: ProductPresentation) {
        viewModelScope.launch {
            inactiveProductUseCase.invoke(mapperPresentationToDomainMapper.map(productPresentation))
                .catch {
                    _inactiveProductFlow.value = CallbackResult.Error(it.localizedMessage)
                }
                .collect {
                    _inactiveProductFlow.value = CallbackResult.Success(it)
                }
        }
    }

    fun findByBarcode(barcode: String) {
        viewModelScope.launch {
            getByBarcode(barcode)
                .catch {
                    _productsFlow.value = CallbackResult.Error(it.localizedMessage)
                }
                .map {
                    it.map { productDomain ->
                        mapperDomain.map(productDomain)
                    }
                }
                .collect {
                    _productsFlow.value = CallbackResult.Success(it)
                }
        }
    }

}