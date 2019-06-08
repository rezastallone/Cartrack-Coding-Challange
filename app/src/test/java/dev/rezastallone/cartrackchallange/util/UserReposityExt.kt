package dev.rezastallone.cartrackchallange.util

import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.UsersRepository

fun UsersRepository.insertUserBlocking(user: Users) = kotlinx.coroutines.runBlocking {
    this@insertUserBlocking.insertUser(user)
}

fun UsersRepository.getUserByIdBlocking(id: Int): Result<Users> = kotlinx.coroutines.runBlocking {
    this@getUserByIdBlocking.getUserById(id)
}

fun UsersRepository.getUserByUsernameAndPasswordBlocking(username:String, password:String): Result<Users> = kotlinx.coroutines.runBlocking {
    this@getUserByUsernameAndPasswordBlocking.getUserByUsernameAndPassword(username, password)
}