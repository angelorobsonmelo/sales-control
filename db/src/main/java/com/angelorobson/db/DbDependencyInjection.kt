package com.angelorobson.db

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val roomModules = module(override = true) {

    single {
        Room.databaseBuilder(
            androidContext(),
            RoomDatabaseSalesControl::class.java,
            "sales_control_db"
        ).fallbackToDestructiveMigration().build()

    }

}


private val lazyLoadRoomsModules by lazy { loadKoinModules(roomModules) }

fun loadRoomsModules() = lazyLoadRoomsModules