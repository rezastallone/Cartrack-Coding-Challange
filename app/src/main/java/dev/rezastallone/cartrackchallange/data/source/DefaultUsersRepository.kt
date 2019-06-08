package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import java.lang.Exception

class DefaultUsersRepository(private val usersLocalDataSource: UsersDataSource) : UsersRepository{

    override fun insertUser(user: Users): Result<Users> {
        return try{
            val insertedID = usersLocalDataSource.insert(user)
            if ( insertedID > 0 ){
                user.id = insertedID
                Result.Success(user)
            } else {
                Result.Error(Exception("Error insert user ${user.username}"))
            }
        }catch (e:Exception){
            Result.Error(e)
        }
    }

    override fun getUserById(id: Int): Result<Users> {
        return try{
            val user = usersLocalDataSource.getUserById(id)
            if ( user != null ){
                Result.Success(user)
            } else {
                Result.Error(Exception("User with id $id not found"))
            }
        }catch (e:Exception){
            Result.Error(e)
        }
    }
}