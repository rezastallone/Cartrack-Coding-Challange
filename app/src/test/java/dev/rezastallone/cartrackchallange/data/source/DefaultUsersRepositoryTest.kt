package dev.rezastallone.cartrackchallange.data.source

import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.FakeUsersLocalDataSource
import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DefaultUsersRepositoryTest {
    private lateinit var usersRepository: DefaultUsersRepository
    private lateinit var userLocalDataSource: FakeUsersLocalDataSource
    private val userForTest = Users(1, "user1", "password", "USA")

    @Before
    fun createRepository() {
        userLocalDataSource = FakeUsersLocalDataSource()
        usersRepository = DefaultUsersRepository(
            userLocalDataSource
        )
    }

    @Test
    fun whenInsertUserAndGetThenFound() = runBlocking {
        usersRepository.insertUser(userForTest)

        val getUserResult = usersRepository.getUserById(userForTest.id)
        Truth.assertThat((getUserResult as? Result.Success)?.data?.id).isNotNull()
        Truth.assertThat((getUserResult as? Result.Success)?.data?.id).isEqualTo(userForTest.id)
    }

    @Test
    fun whenGetUserWithCorrectUsernameAndPasswordThenFound() = runBlocking {
        usersRepository.insertUser(userForTest)

        val getUserResult = usersRepository.getUserByUsernameAndPassword(userForTest.username, userForTest.password)
        Truth.assertThat((getUserResult as? Result.Success)?.data?.id).isNotNull()
        Truth.assertThat((getUserResult as? Result.Success)?.data?.id).isEqualTo(userForTest.id)
    }

    @Test
    fun whenGetUserWrongPasswordThenErrorWrongPassword() = runBlocking {
        usersRepository.insertUser(userForTest)

        val getUserResult = usersRepository.getUserByUsernameAndPassword(userForTest.username, "Wrong password")
        Truth.assertThat((getUserResult as Result.Error).exception.message).isEqualTo(ERROR_WRONG_PASSWORD)
    }

    @Test
    fun whenGetUnregisteredUsernameThenNotFound() = runBlocking {
        val getUserResult = usersRepository.getUserByUsernameAndPassword("name", userForTest.password)
        Truth.assertThat((getUserResult as Result.Error).exception.message).isEqualTo(ERROR_USERNAME_NOT_FOUND)
    }

    @Test
    fun getUnregisteredUserThenErrorNotFound() = runBlocking {
        val getUserResult = usersRepository.getUserById(userForTest.id)
        Truth.assertThat(getUserResult is Result.Error).isTrue()
    }

}