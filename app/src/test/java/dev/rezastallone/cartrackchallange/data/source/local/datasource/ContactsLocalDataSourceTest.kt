package dev.rezastallone.cartrackchallange.data.source.local.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.data.source.contactForTest
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactsLocalDataSourceTest{
    private lateinit var localDataSource: ContactsLocalDataSource
    private lateinit var database: AppDatabase
    @Before
    fun setup() {
        // using an in-memory database for testing, since it doesn't survive killing the process
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        localDataSource = ContactsLocalDataSource(database)
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun whenEmptyThenEmptyList(){
        val contactList = localDataSource.getContacts(0,1)
        Truth.assertThat(contactList.isEmpty()).isTrue()
    }

    @Test
    fun whenInsertContactThenNotEmpty(){
        localDataSource.insertContact(listOf(contactForTest))
        val contactList = localDataSource.getContacts(0,1)
        Truth.assertThat(contactList.isNotEmpty()).isTrue()
        Truth.assertThat(contactList[0].id).isEqualTo(contactForTest.id)
    }
}