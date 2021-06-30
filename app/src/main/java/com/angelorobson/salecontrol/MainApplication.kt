package com.angelorobson.salecontrol

import android.app.Application
import com.angelorobson.db.loadRoomsModules
import com.angelorobson.product.loadProductModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            loadRoomsModules()
            loadProductModules()
        }
    }
}