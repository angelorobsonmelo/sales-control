package com.angelorobson.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.InsertProductUseCase
import com.angelorobson.product.domain.usecase.impl.InsertProduct
import com.angelorobson.product.presentation.mapper.ObjectDomainToPresentationMapper
import com.angelorobson.product.presentation.mapper.ObjectPresentationDomainMapper
import com.angelorobson.product.presentation.model.ProductPresentation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val useCase: GetProductsUseCase,
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

}