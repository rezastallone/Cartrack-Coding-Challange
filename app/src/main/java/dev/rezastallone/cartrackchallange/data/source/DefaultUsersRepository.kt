package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource

class DefaultUsersRepository(private val usersLocalDataSource: UsersDataSource) : UsersRepository{

    override fun insertUser(user: Users) {
        usersLocalDataSource.insert(user)
    }
}