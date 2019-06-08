package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import java.lang.Exception

class DefaultUsersRepository(private val usersLocalDataSource: UsersDataSource) : UsersRepository{

    override fun insertUser(user: Users) {
        usersLocalDataSource.insert(user)
    }

    override fun getUserById(id: Int): Result<Users> {
        val user = usersLocalDataSource.getUserById(id)
        return if ( user != null ){
            Result.Success(user)
        } else {
            Result.Error(Exception("User with id $id not found"))
        }
    }
}