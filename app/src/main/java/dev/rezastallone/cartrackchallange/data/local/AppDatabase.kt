package dev.rezastallone.cartrackchallange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.local.dao.UsersDao

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object{
        const val DATABASE_NAME = "cartrack.db"
    }
}