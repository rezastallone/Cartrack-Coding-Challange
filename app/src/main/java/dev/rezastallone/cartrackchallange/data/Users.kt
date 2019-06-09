package dev.rezastallone.cartrackchallange.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",
    indices = arrayOf(Index(value = ["username"], unique = true)))
data class Users(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "username")
    val username: String = "",
    val password: String = "",
    val countryOfOrigin: String = ""
) {
    override fun toString(): String {
        return "$id $username $password $countryOfOrigin"
    }
}