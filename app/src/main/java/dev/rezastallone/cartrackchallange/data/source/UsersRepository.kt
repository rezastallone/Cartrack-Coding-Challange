package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Users

interface UsersRepository{
    fun insertUser(user:Users)
}