package dev.rezastallone.cartrackchallange.ui.signin

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.rezastallone.cartrackchallange.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInFragmentTest{

    @Test
    fun displaySignInForm_OnFragmentDisplay(){
        launchFragmentInContainer<SignInFragment>(Bundle(), R.style.AppTheme)
        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).check(matches(isDisplayed()))
    }

}