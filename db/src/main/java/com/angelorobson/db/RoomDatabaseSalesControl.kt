package com.angelorobson.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angelorobson.db.features.product.dao.ProductDao
import com.angelorobson.db.features.product.entities.ProductEntity

/**
 * The Room database that contains the tables
 */
@Database(entities = [ProductEntity::class], version = 1)
abstract class RoomDatabaseSalesControl : RoomDatabase() {

    abstract fun productDao(): ProductDao

}
