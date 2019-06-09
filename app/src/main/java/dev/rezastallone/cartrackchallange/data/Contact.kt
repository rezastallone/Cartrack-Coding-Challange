package dev.rezastallone.cartrackchallange.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded
    val address: Address,
    val phone: String,
    val website: String,
    @Embedded(prefix = "company")
    val company: Company
)