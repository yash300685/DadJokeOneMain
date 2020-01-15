package com.onemain.challenge

import android.app.Application
import com.onemain.challenge.koinmodules.appModule
import com.onemain.challenge.koinmodules.networkModule
import com.onemain.challenge.ui.fragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DadJokesApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DadJokesApplication)
            modules(listOf(appModule, networkModule, fragmentModule))
        }
    }
}