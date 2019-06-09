package dev.rezastallone.cartrackchallange.injection

import androidx.room.Room
import dev.rezastallone.cartrackchallange.data.source.DefaultUsersRepository
import dev.rezastallone.cartrackchallange.data.source.UsersRepository
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import dev.rezastallone.cartrackchallange.data.source.local.datasource.ContactsDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.ContactsLocalDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersLocalDataSource
import dev.rezastallone.cartrackchallange.ui.signin.SigninViewModel
import dev.rezastallone.cartrackchallange.ui.signup.SignupViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule by lazy  {
     module {

        single<AppDatabase> {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .build()
        }

        single<UsersDataSource> { UsersLocalDataSource(get()) }
        single<ContactsDataSource> { ContactsLocalDataSource(get()) }
        single<UsersRepository> { DefaultUsersRepository(get())}
        viewModel { SignupViewModel(get()) }
        viewModel { SigninViewModel(get()) }
    }
}