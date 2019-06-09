package dev.rezastallone.cartrackchallange.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.FakeUsersRepository
import dev.rezastallone.cartrackchallange.util.LiveDataTestUtil
import dev.rezastallone.cartrackchallange.util.ViewModelScopeMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignupViewModelTest{
    // Subject under test
    private lateinit var signupViewModel: SignupViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var tasksRepository: FakeUsersRepository

    // A CoroutineContext that can be controlled from tests
    private val testContext = TestCoroutineContext()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesMainDispatcherRule = ViewModelScopeMainDispatcherRule(testContext)

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val userForTest = Users(1, "user1", "password", "USA")

    @Before
    fun setupViewModel() {
        tasksRepository = FakeUsersRepository()
        signupViewModel = SignupViewModel(tasksRepository)
    }

    @Test
    fun insertNewUser_insertedSuccesfully(){
        signupViewModel.insertUser(userForTest)

        testContext.triggerActions()

        val insertUserResult = LiveDataTestUtil.getValue(signupViewModel.insertUserLiveData)
        Truth.assertThat(insertUserResult is Result.Success).isTrue()
        Truth.assertThat((insertUserResult as Result.Success).data.id).isEqualTo(userForTest.id)
    }

    @Test
    fun insertAlreadyExistingUser_showError(){
        signupViewModel.insertUser(userForTest)
        testContext.triggerActions()
        signupViewModel.insertUser(userForTest)
        testContext.triggerActions()

        val insertUserResult = LiveDataTestUtil.getValue(signupViewModel.insertUserLiveData)
        Truth.assertThat(insertUserResult is Result.Error).isTrue()
        Truth.assertThat((insertUserResult as Result.Error).exception.message.toString()).isEqualTo("User already exist")
    }

    @Test
    fun onInsertUser_showLoading(){
        signupViewModel.insertUser(userForTest)

        val insertUserResult = LiveDataTestUtil.getValue(signupViewModel.insertUserLiveData)
        Truth.assertThat(insertUserResult is Result.Loading<*>).isTrue()
    }
}