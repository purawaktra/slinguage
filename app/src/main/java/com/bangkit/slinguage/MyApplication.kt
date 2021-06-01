package com.bangkit.slinguage

import android.app.Application
import com.bangkit.slinguage.di.firebaseModule
import com.bangkit.slinguage.di.repositoryModule
import com.bangkit.slinguage.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    firebaseModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}