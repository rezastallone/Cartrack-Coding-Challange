package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase

class UsersLocalDataSource(private val db: AppDatabase): UsersDataSource{
    override fun insert(user: Users) {
        db.usersDao().insert(user)
    }
}