package dev.rezastallone.cartrackchallange.injection

import androidx.room.Room
import dev.rezastallone.cartrackchallange.data.source.DefaultUsersRepository
import dev.rezastallone.cartrackchallange.data.source.UsersRepository
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersLocalDataSource
import dev.rezastallone.cartrackchallange.ui.signin.SigninViewModel
import dev.rezastallone.cartrackchallange.ui.signup.SignupViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val roomTestModule by lazy  {
    module {
        single<AppDatabase> {
            Room.inMemoryDatabaseBuilder(androidContext(), AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }

        single<UsersDataSource> { UsersLocalDataSource(get()) }
        single<UsersRepository> { DefaultUsersRepository(get()) }
        viewModel { SignupViewModel(get()) }
        viewModel { SigninViewModel(get()) }
    }
}