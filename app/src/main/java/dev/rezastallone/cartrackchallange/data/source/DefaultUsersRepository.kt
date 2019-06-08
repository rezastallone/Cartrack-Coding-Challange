package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.constant.ERROR_USER_NOT_FOUND
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import dev.rezastallone.cartrackchallange.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultUsersRepository(private val usersLocalDataSource: UsersDataSource) : UsersRepository{

    override suspend fun insertUser(user: Users): Result<Users> {
        val result = withContext(Dispatchers.IO){
            try{
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
        return result
    }

    override suspend fun getUserById(id: Int): Result<Users> {
        return withContext(Dispatchers.IO){
            try{
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

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<Users> {
        return withContext(Dispatchers.IO){
            try{
                val user = usersLocalDataSource.getUserByUsernameAndPassword(username, password)
                if ( user != null ){
                    Result.Success(user)
                } else {
                    Result.Error(Exception(ERROR_USER_NOT_FOUND))
                }
            }catch (e:Exception){
                Result.Error(e)
            }
        }
    }
}