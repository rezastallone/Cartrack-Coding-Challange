package dev.rezastallone.cartrackchallange.injection

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

object KoinInitializer {
    fun init(androidContext: Context) {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidLogger()
                androidContext(androidContext)
                modules(appModule)
            }
        }
    }
}