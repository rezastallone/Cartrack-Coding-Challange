package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase

class UsersLocalDataSource(db: AppDatabase) : UsersDataSource {
    private val userDao = db.usersDao()
    override fun insert(user: Users): Int {
        return userDao.insert(user).toInt()
    }

    override fun getUserById(id: Int): Users? {
        return userDao.getUserById(id)
    }

    override fun getUserByUsernameAndPassword(username: String, password: String): Users? {
        val userToCheck = userDao.getUserByUsername(username)
        return if ( userToCheck != null ){
            if ( userToCheck.password == password){
                userToCheck
            } else {
                throw Exception(ERROR_WRONG_PASSWORD)
            }
        } else {
            throw Exception(ERROR_USERNAME_NOT_FOUND)
        }
    }
}