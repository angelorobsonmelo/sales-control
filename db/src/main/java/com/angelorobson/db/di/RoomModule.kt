package com.angelorobson.db.di

import androidx.room.Room
import com.angelorobson.db.RoomDatabaseSalesControl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val roomModules = module(override = true) {

    single {
        Room.databaseBuilder(
            androidContext(),
            RoomDatabaseSalesControl::class.java,
            "sales_control_db"
        ).fallbackToDestructiveMigration()

    }

}
