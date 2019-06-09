package dev.rezastallone.cartrackchallange.injection

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.rezastallone.cartrackchallange.data.source.ContactsRepository
import dev.rezastallone.cartrackchallange.data.source.DefaultContactsRepository
import dev.rezastallone.cartrackchallange.data.source.DefaultUsersRepository
import dev.rezastallone.cartrackchallange.data.source.UsersRepository
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import dev.rezastallone.cartrackchallange.data.source.local.datasource.ContactsDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.ContactsLocalDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersLocalDataSource
import dev.rezastallone.cartrackchallange.data.source.remote.RemoteClient
import dev.rezastallone.cartrackchallange.data.source.remote.RemoteService
import dev.rezastallone.cartrackchallange.ui.signin.SigninViewModel
import dev.rezastallone.cartrackchallange.ui.signup.SignupViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule by lazy {
    module {

        single<AppDatabase> {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .build()
        }
        single<Gson> {
            GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()
        }
        single<RemoteClient> {
            RemoteService.getRemoteService(get())
        }

        single<UsersDataSource> { UsersLocalDataSource(get()) }
        single<ContactsDataSource> { ContactsLocalDataSource(get()) }

        single<UsersRepository> { DefaultUsersRepository(get()) }
        single<ContactsRepository> { DefaultContactsRepository(get(),get()) }

        viewModel { SignupViewModel(get()) }
        viewModel { SigninViewModel(get()) }
    }
}