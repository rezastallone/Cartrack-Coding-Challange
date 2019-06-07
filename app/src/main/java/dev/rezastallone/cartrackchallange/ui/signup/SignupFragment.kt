package dev.rezastallone.cartrackchallange.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.rezastallone.cartrackchallange.R
import kotlinx.android.synthetic.main.signup_fragment.*

class SignupFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToSigninNavigation()
        setupCountryListSpinner()
        setupSignUpButton()
    }

    private fun setupSignUpButton() {
        button_signup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        if ( isFormValid() ){

        }
    }

    private fun isFormValid(): Boolean {
        if (edittext_username.text.isNullOrEmpty()) {
            edittext_username.error = getString(R.string.error_username_empty)
            return false
        }

        if (edittext_password.text.isNullOrEmpty()) {
            edittext_password.error = getString(R.string.error_password_empty)
            return false
        }

        if ( ! isPasswordMatch() ) {
            edittext_password_confirm.error = getString(R.string.error_password_not_match)
            return false
        }

        return true
    }

    private fun isPasswordMatch(): Boolean {
        val passwordToCheck = edittext_password.text ?: ""
        val confirmPasswordToCheck = edittext_password_confirm.text ?: ""
        return passwordToCheck == confirmPasswordToCheck
    }

    private fun setupCountryListSpinner() {
        val spinnerCountryAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        )
        spinnerCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_country.adapter = spinnerCountryAdapter
        spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }
    }

    private fun setupToSigninNavigation() {
        textview_to_signin.setOnClickListener {
            val action = SignupFragmentDirections.actionSigninAccount()
            findNavController().navigate(action)
        }
    }
}