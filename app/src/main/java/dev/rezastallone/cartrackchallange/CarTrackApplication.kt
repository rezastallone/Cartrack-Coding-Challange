package dev.rezastallone.cartrackchallange

import android.app.Application
import dev.rezastallone.cartrackchallange.injection.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class CarTrackApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        if (GlobalContext.getOrNull() == null) {
            startKoin{
                androidLogger()
                androidContext(this@CarTrackApplication)
                modules(appModule)
            }
        }
    }
}