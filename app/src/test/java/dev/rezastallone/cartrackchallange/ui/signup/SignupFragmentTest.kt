package dev.rezastallone.cartrackchallange.ui.signup


import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.FakeUsersRepository
import dev.rezastallone.cartrackchallange.data.source.UsersRepository
import dev.rezastallone.cartrackchallange.injection.appModule
import dev.rezastallone.cartrackchallange.util.EspressoIdlingResource
import dev.rezastallone.cartrackchallange.util.getUserByIdBlocking
import dev.rezastallone.cartrackchallange.util.hasTextInputLayoutError
import dev.rezastallone.cartrackchallange.util.insertUserBlocking
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class SignupFragmentTest{

    private lateinit var scenario: FragmentScenario<SignupFragment>
    private lateinit var repository: UsersRepository

    @Before
    fun setupFragment(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        repository = FakeUsersRepository()
        scenario = launchFragmentInContainer<SignupFragment>(Bundle(), R.style.AppTheme)
    }

    @After
    fun cleanup(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        stopKoin()
    }


    @Test
    fun displaySignupForm_OnFragmentDisplay(){
        launchFragmentInContainer<SignupFragment>(Bundle(), R.style.AppTheme)
        onView(ViewMatchers.withId(R.id.button_signup)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.edittext_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.edittext_password_confirm)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.edittext_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickToSignInTextView_navigateToSignInFragment(){
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(ViewMatchers.withId(R.id.textview_to_signin)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            SignupFragmentDirections.actionSigninAccount()
        )
    }

    @Test
    fun displayError_whenUsernameIsEmpty(){
        onView(ViewMatchers.withId(R.id.button_signup)).perform(click())
        onView(ViewMatchers.withId(R.id.edittext_username)).check(matches(hasTextInputLayoutError()))
    }

    @Test
    fun displayError_whenPasswordIsEmpty(){
        onView(ViewMatchers.withId(R.id.edittext_username)).perform(replaceText("username"))
        onView(ViewMatchers.withId(R.id.button_signup)).perform(click())
        onView(ViewMatchers.withId(R.id.edittext_password)).check(matches(hasTextInputLayoutError()))
    }

    @Test
    fun displayError_whenPasswordNotMatch(){
        onView(ViewMatchers.withId(R.id.edittext_username)).perform(replaceText("username"))
        onView(ViewMatchers.withId(R.id.edittext_password)).perform(replaceText("password"))
        onView(ViewMatchers.withId(R.id.edittext_password_confirm)).perform(replaceText("notpassword"))
        onView(ViewMatchers.withId(R.id.button_signup)).perform(click())
        onView(ViewMatchers.withId(R.id.edittext_password_confirm)).check(matches(hasTextInputLayoutError()))
    }

}