package dev.rezastallone.cartrackchallange.injection

import androidx.room.Room
import dev.rezastallone.cartrackchallange.data.source.DefaultUsersRepository
import dev.rezastallone.cartrackchallange.data.source.UsersRepository
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    single<UsersDataSource> { UsersLocalDataSource(get()) }
    single<UsersRepository> { DefaultUsersRepository(get())}
}