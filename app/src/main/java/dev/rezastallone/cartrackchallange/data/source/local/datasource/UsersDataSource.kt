package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.data.Users

interface UsersDataSource {
    fun insert(user:Users): Int
    fun getUserById(id: Int) : Users?
    fun getUserByUsernameAndPassword(username: String, password: String): Users?
}