package dev.rezastallone.cartrackchallange.util

import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun hasTextInputLayoutError(): Matcher<View> {
    return object : TypeSafeMatcher<View>(){
        override fun describeTo(description: Description?) {}
        override fun matchesSafely(item: View?): Boolean {
            if (item !is TextInputEditText) {
                return false
            }
            return ! item.error.isNullOrEmpty()
        }
    }
}