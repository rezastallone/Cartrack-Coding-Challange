package dev.rezastallone.cartrackchallange.data.source.local.datasource.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.contactForTest
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ContactsDaoTest {

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun whenEmptyThenEmptyList(){
        val contactList = database.contactsDao().getContacts(1,0)
        Truth.assertThat(contactList.isEmpty()).isTrue()
    }

    @Test
    fun whenInsertContactThenNotEmpty(){
        database.contactsDao().insert(listOf(contactForTest))
        val contactList = database.contactsDao().getContacts(1,0)
        Truth.assertThat(contactList.isNotEmpty()).isTrue()
        Truth.assertThat(contactList[0].id).isEqualTo(contactForTest.id)
    }
}