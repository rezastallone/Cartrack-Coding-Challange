package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users

interface UsersRepository {
    suspend fun insertUser(user: Users): Result<Users>
    suspend fun getUserById(id: Int): Result<Users>
    suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<Users>
}