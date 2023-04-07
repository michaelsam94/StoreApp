package com.example.storeapp

import android.app.Application
import com.example.storeapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(roomModule, networkModule, apiServiceModule, repoModule, viewModelModule)
        }
    }
}