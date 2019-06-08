package dev.rezastallone.cartrackchallange.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users (
    @PrimaryKey val id: Int,
    val username: String = "",
    val password: String = "",
    val countryOfOrigin: String = ""
)