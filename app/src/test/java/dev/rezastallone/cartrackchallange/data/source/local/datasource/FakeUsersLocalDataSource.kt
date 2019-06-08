package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
import java.lang.Exception
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
        val entriesFound = usersData.filter {
            it.value.username == username
        }
        return if ( entriesFound.isNotEmpty() ){
            entriesFound[0]
        } else {
            null
        }
    }
}