package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users

interface UsersRepository {
    fun insertUser(user: Users): Result<Users>
    fun getUserById(id: Int): Result<Users>
}