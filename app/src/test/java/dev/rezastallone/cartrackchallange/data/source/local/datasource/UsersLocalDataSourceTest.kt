package dev.rezastallone.cartrackchallange.data.source.local.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
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
    private val userForTest = Users(1, "michael", "password", "USA")

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
    fun whenGetUnregisteredUserThenNotFound() = runBlocking {
        val userToCheck = localDataSource.getUserById(userForTest.id)

        Truth.assertThat(userToCheck).isNull()
    }

    @Test
    fun whenGetUnregisteredUsernameThenErrorNotFound() = runBlocking {
        try {
            val userToCheck = localDataSource.getUserByUsernameAndPassword(userForTest.username, userForTest.password)
        }catch (e:Exception){
            Truth.assertThat(e.message).isEqualTo(ERROR_USERNAME_NOT_FOUND)
        }
    }

    @Test
    fun whenGetUserWithWrongPasswordThenErrorWrongPassword() = runBlocking {
        localDataSource.insert(userForTest)
        try {
            val userToCheck = localDataSource.getUserByUsernameAndPassword(userForTest.username, "wrong password")
        }catch (e:Exception){
            Truth.assertThat(e.message).isEqualTo(ERROR_WRONG_PASSWORD)
        }
    }

    @Test
    fun whenInsertUserAndGetThenUserFound() = runBlocking {
        localDataSource.insert(userForTest)

        val userToCheck = localDataSource.getUserById(userForTest.id)

        Truth.assertThat(userToCheck).isNotNull()
        Truth.assertThat(userToCheck?.id).isEqualTo(userToCheck?.id)
    }

    @Test
    fun whenGetUserWithCorrectUsernameAndPasswordThenFound() = runBlocking {
        localDataSource.insert(userForTest)

        val userToCheck = localDataSource.getUserByUsernameAndPassword(userForTest.username, userForTest.password)

        Truth.assertThat(userToCheck).isNotNull()
        Truth.assertThat(userToCheck?.id).isEqualTo(userToCheck?.id)
    }
}