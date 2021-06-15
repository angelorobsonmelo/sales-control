package com.angelorobson.product

import com.angelorobson.db.RoomDatabaseSalesControl
import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.ProductRepositoryImpl
import com.angelorobson.product.data.datasource.local.LocalDataSource
import com.angelorobson.product.data.datasource.local.LocalDataSourceImpl
import com.angelorobson.product.data.mapper.ObjectToDomainMapper
import com.angelorobson.product.domain.mapper.ObjectToPresentationMapper
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.impl.GetProducts
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val daoModule = module(override = true) {
    single { get<RoomDatabaseSalesControl>().productDao() }
}

private val mapperModules = module(override = true) {
    single { ObjectToDomainMapper() }
    single { ObjectToPresentationMapper() }
}

private val localDataSourceModule = module(override = true) {
    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
}

private val repositoryModules = module(override = true) {
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}

private val useCaseModules = module(override = true) {
    single<GetProductsUseCase> { GetProducts(get()) }
}

private val viewModelModule = module(override = true) {
    single { ProductsViewModel(get()) }
}

private val productModules =
    mapperModules + daoModule + localDataSourceModule + repositoryModules + useCaseModules + viewModelModule

private val lazyLoadProductModules by lazy { loadKoinModules(productModules) }

fun loadProductModules() = lazyLoadProductModules
