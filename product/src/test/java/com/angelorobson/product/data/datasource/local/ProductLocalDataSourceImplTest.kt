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
class ProductLocalDataSourceImplTest {


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
    fun insertProducts_whenSuccess_returnedListOfProducts() = runBlocking {
        // Arrange
        val products = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // Action
        dao.insert(products)

        // Asserts
        assertThat(products.size, equalTo(dao.getAll().size))
    }

    @Test
    fun insert_products_afterInserted_returnId() = runBlocking {
        // Arrange
        val productEntity = ProductEntity(
            name = "product",
            price = 10.0,
            barcode = "12",
            description = "description",
            isActive = true
        )

        // Action
        val id = dao.insert(productEntity)

        // Assert
        assertTrue(id != 0L)
    }

    @Test
    fun update_product_afterUpdated_returnProductUpdated() = runBlocking {
        // Arrange
        var productEntityToUpdated: ProductEntity?
        val nameExpected = "product updated"
        val productEntity = ProductEntity(
            name = "product",
            price = 10.0,
            barcode = "12",
            description = "description",
            isActive = true
        )

        // Action
        dao.insert(productEntity)
        productEntityToUpdated = dao.getAll().first().copy(name = "product updated")
        dao.update(productEntityToUpdated)

        productEntityToUpdated = dao.getAll().first()

        // Assert
        assertEquals(nameExpected, productEntityToUpdated.name)
    }

    @Test
    fun getAll_whenSuccess_getProductsList() = runBlocking {
        // Arrange
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // Action
        dao.insert(productsExpected)
        val productActual = dao.getAll()

        // Asserts
        assertEquals(productsExpected.size, productActual.size)
    }

    @Test
    fun findByBarcode_shouldReturnCorrectProduct() = runBlocking {
        // Arrange
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

        // Action
        dao.insert(productsExpected)
        val productActual = dao.findByBarcode(barcodeExpected)

        // Asserts
        assertEquals(barcodeExpected, productActual.first().barcode)
    }

    @Test
    fun findById() = runBlocking {
        // Arrange
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // Action
        dao.insert(productsExpected)
        val productReturned = dao.findByBarcode("123")
        val productActual = dao.findById(productReturned.first().id)

        // Asserts
        assertNotNull(productActual)
    }

    @Test
    fun findByName() = runBlocking {
        // Arrange
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // Action
        dao.insert(productsExpected)
        val productReturned = dao.findByName("product 2")

        // Asserts
        assertNotNull(productReturned)
    }

    @Test
    fun findByTerm() = runBlocking {
        // Arrange
        val productsExpected = (1..10).map { i ->
            ProductEntity(
                name = "product $i",
                price = 10.0 + i,
                barcode = "12$i",
                description = "description $i",
                isActive = true
            )
        }

        // Action
        dao.insert(productsExpected)
        val productReturned = dao.findByTerm("%pro%")

        // Asserts
        assertEquals(productsExpected.size, productReturned.size)
    }

    @Test
    fun inactivateProduct() = runBlocking {
        // Arrange
        val productEntity = ProductEntity(
            name = "product",
            price = 10.0,
            barcode = "12",
            description = "description",
            isActive = true
        )

        // Action
        dao.insert(productEntity)
        val productReturned = dao.findByName(productEntity.name)
        val copy = productReturned.copy(isActive = false)
        dao.update(copy)
        val productsEntityToUpdated: List<ProductEntity> = dao.getAll()

        // Assert
        assertTrue(productsEntityToUpdated.isEmpty())
    }

}