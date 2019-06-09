package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import java.lang.Exception
import java.util.LinkedHashMap

class FakeUsersRepository: UsersRepository{

    var usersData: LinkedHashMap<Int, Users> = LinkedHashMap()

    override suspend fun insertUser(user: Users): Result<Users> {
        return if (userExist(user)){
            Result.Error(Exception("User already exist"))
        } else {
            usersData[user.id] = user
            Result.Success(user)
        }
    }

    private fun userExist(user: Users) = usersData[user.id] != null

    override suspend fun getUserById(id: Int): Result<Users> {
        val user = usersData[id]
        return if ( user != null ){
            Result.Success(user)
        } else {
            Result.Error(Exception("User not found"))
        }
    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<Users> {
        val entriesFound = usersData.filter {
            it.value.username == username
        }
        return if (entriesFound.isNotEmpty()){
            val userFound = entriesFound[0]
            if ( userFound != null && userFound.password == password){
                Result.Success(userFound)
            } else {
                throw Exception(ERROR_WRONG_PASSWORD)
            }
        } else {
            throw Exception(ERROR_USERNAME_NOT_FOUND)
        }
    }
}