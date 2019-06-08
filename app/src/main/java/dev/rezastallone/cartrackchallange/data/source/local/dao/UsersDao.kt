package dev.rezastallone.cartrackchallange.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rezastallone.cartrackchallange.data.Users

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Users)

    @Query("Select * from users where users.id = :id ")
    fun getUserById(id: Int) : Users?
}