package dev.rezastallone.cartrackchallange.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import dev.rezastallone.cartrackchallange.data.Contact

@Dao
interface ContactsDao {
    @Query("Select * From contacts Order By id")
    fun getContacts(): List<Contact>
}