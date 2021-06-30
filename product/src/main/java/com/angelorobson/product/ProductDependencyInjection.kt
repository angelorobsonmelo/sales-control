package com.angelorobson.product

import com.angelorobson.db.RoomDatabaseSalesControl
import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.ProductRepositoryImpl
import com.angelorobson.product.data.datasource.local.LocalDataSource
import com.angelorobson.product.data.datasource.local.LocalDataSourceImpl
import com.angelorobson.product.data.mapper.ObjectDataToEntityMapper
import com.angelorobson.product.data.mapper.ObjectEntityToDataMapper
import com.angelorobson.product.data.mapper.ObjectProductSaveDataToEntityMapper
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.mapper.ObjectDomainToDataMapper
import com.angelorobson.product.domain.mapper.ObjectProductSaveDomainToDataMapper
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.InsertProductUseCase
import com.angelorobson.product.domain.usecase.impl.GetProductByName
import com.angelorobson.product.domain.usecase.impl.GetProducts
import com.angelorobson.product.domain.usecase.impl.InsertProduct
import com.angelorobson.product.presentation.mapper.ObjectDomainToPresentationMapper
import com.angelorobson.product.presentation.mapper.ObjectPresentationToDomainMapper
import com.angelorobson.product.presentation.mapper.ObjectSaveProductPresentationToDomainMapper
import com.angelorobson.product.presentation.viewmodel.AddProductViewModel
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val daoModule = module(override = true) {
    single { get<RoomDatabaseSalesControl>().productDao() }
}

private val mapperModules = module(override = true) {
    single { ObjectEntityToDataMapper() }
    single { ObjectDataToDomainMapper() }
    single { ObjectDomainToPresentationMapper() }
    single { ObjectDataToEntityMapper() }
    single { ObjectDomainToDataMapper() }
    single { ObjectPresentationToDomainMapper() }
    single { ObjectSaveProductPresentationToDomainMapper() }
    single { ObjectProductSaveDomainToDataMapper() }
    single { ObjectProductSaveDataToEntityMapper() }
}

private val localDataSourceModule = module(override = true) {
    single<LocalDataSource> { LocalDataSourceImpl(get(), get(), get()) }
}

private val repositoryModules = module(override = true) {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}

private val useCaseModules = module(override = true) {
    single<GetProductsUseCase> { GetProducts(get(), get()) }
    single<InsertProductUseCase> { InsertProduct(get(), get()) }
    single<GetProductByNameUseCase> { GetProductByName(get(), get()) }
}

private val viewModelModule = module(override = true) {
    viewModel { ProductsViewModel(get(), get(), get()) }
    viewModel { AddProductViewModel(get(), get(), androidApplication()) }
}

private val productModules =
    mapperModules + daoModule + localDataSourceModule + repositoryModules + useCaseModules + viewModelModule

private val lazyLoadProductModules by lazy { loadKoinModules(productModules) }

fun loadProductModules() = lazyLoadProductModules
