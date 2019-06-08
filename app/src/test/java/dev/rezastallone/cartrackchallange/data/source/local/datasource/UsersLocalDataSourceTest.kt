package dev.rezastallone.cartrackchallange.data.source.local.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersLocalDataSourceTest{
    private lateinit var localDataSource: UsersLocalDataSource
    private lateinit var database: AppDatabase
    private val userForTest = Users(1, "user1", "password", "USA")

    @Before
    fun setup() {
        // using an in-memory database for testing, since it doesn't survive killing the process
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        localDataSource = UsersLocalDataSource(database)
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun insertUser_retrievesUser() = runBlocking {
        localDataSource.insert(userForTest)

        val userToCheck = localDataSource.getUserById(userForTest.id)

        Truth.assertThat(userToCheck).isNotNull()
        Truth.assertThat(userToCheck?.id).isEqualTo(userToCheck?.id)
    }
}