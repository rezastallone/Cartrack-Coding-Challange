package dev.rezastallone.cartrackchallange.data.source.local.datasource.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class UsersDaoTest {
    private val userForTest = Users(1, "michael", "password", "USA")
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
    fun WheninsertUserAndGetUserThenFound() = runBlocking {
        database.usersDao().insert(userForTest)
        val user = database.usersDao().getUserById(userForTest.id)
        Truth.assertThat(user).isNotNull()
    }

    @Test
    fun WhenNoInsertThenNotFound() = runBlocking {
        val user = database.usersDao().getUserById(userForTest.id)
        Truth.assertThat(user).isNull()
    }

    @Test
    fun WhenNoUsernameThenNotFound() = runBlocking {
        val user = database.usersDao().getUserByUsername("roger")
        Truth.assertThat(user).isNull()
    }

    @Test
    fun WhenUsernameExistThenFound() = runBlocking {
        database.usersDao().insert(userForTest)
        val user = database.usersDao().getUserByUsername(userForTest.username)
        Truth.assertThat(user).isNotNull()
    }

}