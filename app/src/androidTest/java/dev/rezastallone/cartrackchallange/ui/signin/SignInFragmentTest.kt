package dev.rezastallone.cartrackchallange.ui.signin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.rezastallone.cartrackchallange.R
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class SignInFragmentTest{

    @Test
    fun displaySignInForm_OnFragmentDisplay(){
        launchFragmentInContainer<SignInFragment>(Bundle(), R.style.AppTheme)
        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).check(matches(isDisplayed()))
    }

    @Test
    fun clickToSignUpTextView_navigateToSignUpFragment(){
        val scenario = launchFragmentInContainer<SignInFragment>(Bundle(), R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.textview_to_signup)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            SignInFragmentDirections.actionSignupAccount()
        )
    }

}