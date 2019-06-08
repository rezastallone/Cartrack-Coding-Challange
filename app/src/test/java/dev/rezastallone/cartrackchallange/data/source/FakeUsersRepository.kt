package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import java.lang.Exception
import java.util.LinkedHashMap

class FakeUsersRepository: UsersRepository{

    var usersData: LinkedHashMap<Int, Users> = LinkedHashMap()

    override fun insertUser(user: Users) {
        usersData[user.id] = user
    }

    override fun getUserById(id: Int): Result<Users> {
        val user = usersData[id]
        return if ( user != null ){
            Result.Success(user)
        } else {
            Result.Error(Exception("User not found"))
        }
    }
}