package com.example.to_do_list.application

import android.app.Application
import com.example.to_do_list.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ToDoApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@ToDoApplication)
            modules(AppModule.appModules())
        }
    }

}