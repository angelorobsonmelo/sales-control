package com.angelorobson.db.features.product.dao

import androidx.room.*
import androidx.room.FtsOptions.Order
import com.angelorobson.db.features.product.entities.ProductEntity


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity): Long

    @Query("SELECT * FROM ProductEntity WHERE isActive = 1 ORDER BY name")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE isActive = 1 AND barcode LIKE :barCode ORDER BY name")
    suspend fun findByBarcode(barCode: String): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE isActive = 1 AND id = :id")
    suspend fun findById(id: Long): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE isActive = 1 AND name = :name")
    suspend fun findByName(name: String): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE isActive = 1 AND name LIKE :name ORDER BY name")
    suspend fun findByTerm(name: String): List<ProductEntity>

    @Update
    suspend fun update(product: ProductEntity)

}