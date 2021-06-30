package com.angelorobson.product.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.R
import com.angelorobson.product.domain.usecase.InsertProductUseCase
import com.angelorobson.product.presentation.mapper.ObjectSaveProductPresentationToDomainMapper
import com.angelorobson.product.presentation.model.ProductToSavePresentation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val useCase: InsertProductUseCase,
    private val mapper: ObjectSaveProductPresentationToDomainMapper,
    application: Application
) : AndroidViewModel(application) {

    var name = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var price = MutableLiveData<String>()

    private val _saveResultFlow = MutableStateFlow<CallbackResult<Long>>(CallbackResult.Loading())
    val saveResultFlow: StateFlow<CallbackResult<Long>> get() = _saveResultFlow

    fun saveProduct(product: ProductToSavePresentation) {
        viewModelScope.launch {
            if (isFormValid(product)) {
                val productDomain = mapper.map(product)
                useCase.invoke(productDomain)
                    .onStart {
                        _saveResultFlow.value = CallbackResult.Loading()
                    }
                    .catch {
                        _saveResultFlow.value = CallbackResult.Error(it.localizedMessage)
                    }.collect {
                        _saveResultFlow.value = CallbackResult.Success(it)
                    }
            }

        }
    }

    private fun isFormValid(product: ProductToSavePresentation): Boolean {
        var valid = true
        val emptyText = getApplication<Application>().getString(R.string.empty_field)
        val greaterThanZero = getApplication<Application>().getString(R.string.greater_than_zero)

        if (product.name.isEmpty()) {
            name.value = emptyText
            valid = false
        }
        if (product.description.isEmpty()) {
            description.value = emptyText
            valid = false
        }

        if (product.price <= 0) {
            price.value = greaterThanZero
            valid = false
        }

        return valid
    }

}