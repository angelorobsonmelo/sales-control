package com.angelorobson.db.features.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelorobson.db.features.product.entities.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity): Long

    @Query("SELECT * FROM ProductEntity ORDER BY name")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE barcode = :barCode")
    suspend fun findByBarcode(barCode: String): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    suspend fun findById(id: Int): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE name = :name")
    suspend fun findByName(name: String): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE name LIKE :name ORDER BY name")
    suspend fun findByTerm(name: String): List<ProductEntity>

}