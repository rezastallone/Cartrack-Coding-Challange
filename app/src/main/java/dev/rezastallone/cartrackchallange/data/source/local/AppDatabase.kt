package dev.rezastallone.cartrackchallange.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.dao.ContactsDao
import dev.rezastallone.cartrackchallange.data.source.local.dao.UsersDao

@Database(entities = [Users::class, Contact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
    abstract fun contactsDao(): ContactsDao

    companion object{
        const val DATABASE_NAME = "cartrack.db"
    }
}