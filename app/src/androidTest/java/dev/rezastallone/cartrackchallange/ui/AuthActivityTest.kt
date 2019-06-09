package dev.rezastallone.cartrackchallange.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase
import dev.rezastallone.cartrackchallange.util.EspressoIdlingResource
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class AuthActivityTest: KoinTest{

    val db: AppDatabase by inject()

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        db.clearAllTables()
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun signupUserNotMatchPassword() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.textview_to_signup)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password_confirm)).perform(ViewActions.typeText("notpassword"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun signupUserInvalidForm() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.textview_to_signup)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password_confirm)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun signupUser() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.textview_to_signup)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).perform(ViewActions.typeText("Roger"))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password_confirm)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun signupAndLogin() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.textview_to_signup)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).perform(ViewActions.typeText("Roger"))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password_confirm)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).perform(ViewActions.typeText("Roger"))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Home")).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun signupAndLoginWrongPassword() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.textview_to_signup)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).perform(ViewActions.typeText("Roger"))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password_confirm)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).perform(ViewActions.typeText("Roger"))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("notpassword"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun signupAndLoginWrongUsername() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.textview_to_signup)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).perform(ViewActions.typeText("Roger"))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password_confirm)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signup)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.edittext_username)).perform(ViewActions.typeText("RogerRoger"))
        Espresso.onView(ViewMatchers.withId(R.id.edittext_password)).perform(ViewActions.typeText("password"), closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.button_signin)).check(matches(ViewMatchers.isDisplayed()))
    }
}