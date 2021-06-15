package com.angelorobson.product

import com.angelorobson.db.RoomDatabaseSalesControl
import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.ProductRepositoryImpl
import com.angelorobson.product.data.datasource.local.LocalDataSource
import com.angelorobson.product.data.datasource.local.LocalDataSourceImpl
import com.angelorobson.product.data.mapper.ObjectToDataMapper
import com.angelorobson.product.domain.mapper.ObjectToDomainMapper
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.impl.GetProducts
import com.angelorobson.product.presentation.mapper.ObjectToPresentationMapper
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val daoModule = module(override = true) {
    single { get<RoomDatabaseSalesControl>().productDao() }
}

private val mapperModules = module(override = true) {
    single { ObjectToDataMapper() }
    single { ObjectToDomainMapper() }
    single { ObjectToPresentationMapper() }
}

private val localDataSourceModule = module(override = true) {
    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
}

private val repositoryModules = module(override = true) {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}

private val useCaseModules = module(override = true) {
    single<GetProductsUseCase> { GetProducts(get(), get()) }
}

private val viewModelModule = module(override = true) {
    viewModel { ProductsViewModel(get(), get()) }
}

private val productModules =
    mapperModules + daoModule + localDataSourceModule + repositoryModules + useCaseModules + viewModelModule

private val lazyLoadProductModules by lazy { loadKoinModules(productModules) }

fun loadProductModules() = lazyLoadProductModules
