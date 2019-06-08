package dev.rezastallone.cartrackchallange.data.source

import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
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
    fun insertUser_userExistOnGetById() {
        usersRepository.insertUser(userForTest)

        val getUserResult = usersRepository.getUserById(userForTest.id)
        Truth.assertThat((getUserResult as? Result.Success)?.data?.id).isNotNull()
        Truth.assertThat((getUserResult as? Result.Success)?.data?.id).isEqualTo(userForTest.id)
    }

    @Test
    fun getUser_returnErrorIfNotFound(){
        val getUserResult = usersRepository.getUserById(userForTest.id)
        Truth.assertThat(getUserResult is Result.Error).isTrue()
    }

}