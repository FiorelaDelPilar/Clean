package com.example.clean

import android.app.Application
import com.example.clean.common.mainModule
import org.koin.core.context.startKoin

class CleanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule)
        }
    }
}