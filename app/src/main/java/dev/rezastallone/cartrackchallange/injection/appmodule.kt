package dev.rezastallone.cartrackchallange.injection

import androidx.room.Room
import dev.rezastallone.cartrackchallange.data.local.AppDatabase
import dev.rezastallone.cartrackchallange.data.local.datasource.UsersDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    single<UsersDataSource> { UsersDataSource(get()) }
}