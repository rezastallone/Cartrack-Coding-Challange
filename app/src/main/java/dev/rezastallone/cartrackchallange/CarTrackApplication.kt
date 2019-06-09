package dev.rezastallone.cartrackchallange

import android.app.Application
import dev.rezastallone.cartrackchallange.injection.KoinInitializer

class CarTrackApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        KoinInitializer.init(this)
    }
}