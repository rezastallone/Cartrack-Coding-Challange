package dev.rezastallone.cartrackchallange.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rezastallone.cartrackchallange.data.Contact

@Dao
interface ContactsDao {
    @Query("Select * From contacts Order By id asc limit :limit Offset :offset ")
    fun getContacts(limit: Int, offset: Int): MutableList<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<Contact>)
}