package dev.rezastallone.cartrackchallange.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import kotlinx.android.synthetic.main.signup_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignupFragment : Fragment() {

    private val signupViewModel: SignupViewModel by viewModel()

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

    private fun signUp() {
        if (isFormValid()) {
            val userToInsert = getUserToInsert()
            val signupProcessToObserve = signupViewModel.insertUser(userToInsert)
            observerSignupProcess(signupProcessToObserve)
        }
    }

    private fun observerSignupProcess(signupProcessToObserve: MutableLiveData<Result<Users>>) {
        signupProcessToObserve.observe(viewLifecycleOwner, Observer { signupResult ->
            when(signupResult){
                is Result.Success -> {
                    onSignupSuccess()
                }
                is Result.Error -> {
                    onSignupError(signupResult.exception)
                }
                is Result.Loading<*> -> {
                    onSignupLoading()
                }
            }
        })
    }

    private fun onSignupLoading() {
        progressbar_signup.visibility = View.VISIBLE
        button_signup.isEnabled = false
    }

    private fun onSignupError(exception: Exception) {
        onSignupStopLoading()
        val messageToCheck = exception.message.toString()
        if (isErrorUsernameTaken(messageToCheck)) {
            edittext_username.error = getString(R.string.error_username_already_taken)
        } else {
            Toast.makeText(context,R.string.error_unknown_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isErrorUsernameTaken(messageToCheck: String) = messageToCheck.contains("username")

    private fun onSignupSuccess() {
        onSignupStopLoading()
        Toast.makeText(context, R.string.success_signup, Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun onSignupStopLoading() {
        progressbar_signup.visibility = View.GONE
        button_signup.isEnabled = true
    }

    private fun getUserToInsert(): Users {
        return Users(
            0,
            edittext_username.text.toString(),
            edittext_password.text.toString(),
            spinner_country.selectedItem as String
        )
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

        if (!isPasswordMatch()) {
            edittext_password_confirm.error = getString(R.string.error_password_not_match)
            return false
        }

        return true
    }

    private fun isPasswordMatch(): Boolean {
        val passwordToCheck = edittext_password.text.toString()
        val confirmPasswordToCheck = edittext_password_confirm.text.toString()
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
    }

    private fun setupToSigninNavigation() {
        textview_to_signin.setOnClickListener {
            navigateToSignin()
        }
    }

    private fun navigateToSignin() {
        val action = SignupFragmentDirections.actionSigninAccount()
        findNavController().navigate(action)
    }
}