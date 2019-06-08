package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.datasource.UsersDataSource
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

}