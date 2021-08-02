package com.angelorobson.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.angelorobson.db.features.product.entities.ProductEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


val roomModules = module(override = true) {

    single {
        var appDatabase: RoomDatabaseSalesControl? = null

        appDatabase = Room.databaseBuilder(
            androidContext(),
            RoomDatabaseSalesControl::class.java,
            "sales_control_db"
        ).fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    if (BuildConfig.DEBUG) {
                        val list = mutableListOf<ProductEntity>()
                        for (i in 1..10) {
                            list.add(
                                ProductEntity(
                                    name = "product $i",
                                    price = 10.0 + i,
                                    barcode = "12$i",
                                    description = "description $i",
                                    isActive = true
                                )
                            )
                        }

                        GlobalScope.launch {
                            appDatabase?.productDao()?.insert(list)
                        }
                    }
                }
            })
            .build()

        appDatabase
    }

}


private val lazyLoadRoomsModules by lazy { loadKoinModules(roomModules) }

fun loadRoomsModules() = lazyLoadRoomsModules