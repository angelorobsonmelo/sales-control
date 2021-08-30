package com.angelorobson.product.data.datasource.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.db.RoomDatabaseSalesControl
import com.angelorobson.db.features.product.dao.ProductDao
import com.angelorobson.db.features.product.entities.ProductEntity
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProductLocalDataSourceTest {


    private lateinit var db: RoomDatabaseSalesControl
    private lateinit var dao: ProductDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabaseSalesControl::class.java).build()
        dao = db.productDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `When getAll in dao should return a list of products`() = runBlocking {
        // Given
        val products = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // When
        dao.insert(products)

        // Then
        assertThat(products.size, equalTo(dao.getAll().size))
    }

    @Test
    fun `When insert product in dao should return id bigger then 0`() = runBlocking {
        // Given
        val productEntity = ProductEntity(
            name = "product",
            price = 10.0,
            barcode = "12",
            description = "description",
            isActive = true
        )

        // When
        val id = dao.insert(productEntity)

        // Then
        assertTrue(id != 0L)
    }

    @Test
    fun `When update product should return from dao a product updated`() = runBlocking {
        // Given
        var productEntityToUpdated: ProductEntity?
        val nameExpected = "product updated"
        val productEntity = ProductEntity(
            name = "product",
            price = 10.0,
            barcode = "12",
            description = "description",
            isActive = true
        )

        // When
        dao.insert(productEntity)
        productEntityToUpdated = dao.getAll().first().copy(name = "product updated")
        dao.update(productEntityToUpdated)

        productEntityToUpdated = dao.getAll().first()

        // Then
        assertEquals(nameExpected, productEntityToUpdated.name)
    }

    @Test
    fun `When getAll in database should return product list`() = runBlocking {
        // Given
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // When
        dao.insert(productsExpected)
        val productActual = dao.getAll()

        // Then
        assertEquals(productsExpected.size, productActual.size)
    }

    @Test
    fun `When findByBarcode should return a product list`() = runBlocking {
        // Given
        val barcodeExpected = "121"
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // When
        dao.insert(productsExpected)
        val productActual = dao.findByBarcode(barcodeExpected)

        // Then
        assertEquals(barcodeExpected, productActual.first().barcode)
    }

    @Test
    fun `When findById should return a product`() = runBlocking {
        // Given
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // When
        dao.insert(productsExpected)
        val productReturned = dao.findByBarcode("123")
        val productActual = dao.findById(productReturned.first().id)

        // Then
        assertNotNull(productActual)
    }

    @Test
    fun `When findByName should return a product`() = runBlocking {
        // Given
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // When
        dao.insert(productsExpected)
        val productReturned = dao.findByName("product 2")

        // Then
        assertNotNull(productReturned)
    }

    @Test
    fun `When findByTerm should return a list of products`() = runBlocking {
        // Given
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // When
        dao.insert(productsExpected)
        val productReturned = dao.findByTerm("%pro%")

        // Then
        assertEquals(productsExpected.size, productReturned.size)
    }

    @Test
    fun `When inactivateProduct should not return product`() = runBlocking {
        // Given
        val productEntity = ProductEntity(
            name = "product",
            price = 10.0,
            barcode = "12",
            description = "description",
            isActive = true
        )

        // When
        dao.insert(productEntity)
        val productReturned = dao.findByName(productEntity.name)
        val copy = productReturned.copy(isActive = false)
        dao.update(copy)
        val productsEntityToUpdated: List<ProductEntity> = dao.getAll()

        // Then
        assertTrue(productsEntityToUpdated.isEmpty())
    }

}