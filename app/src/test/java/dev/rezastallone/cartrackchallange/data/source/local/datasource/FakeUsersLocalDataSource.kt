package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
import dev.rezastallone.cartrackchallange.data.Users
import java.util.*

class FakeUsersLocalDataSource : UsersDataSource {

    var usersData: LinkedHashMap<Int, Users> = LinkedHashMap()

    override fun getUserById(id: Int): Users? {
        return usersData[id]
    }

    override fun insert(user: Users): Int {
        usersData[user.id] = user
        return user.id
    }

    override fun getUserByUsernameAndPassword(username: String, password: String): Users? {
        for (entry in usersData) {
            val userToCheck = entry.value
            if (userToCheck.username == username ) {
                if ( userToCheck.password == password ){
                    return userToCheck
                }
                throw Exception(ERROR_WRONG_PASSWORD)
            }
        }

        throw Exception(ERROR_USERNAME_NOT_FOUND)
    }
}