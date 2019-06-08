package dev.rezastallone.cartrackchallange.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import kotlinx.android.synthetic.main.signin_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(){

    private val signinViewModel: SigninViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToSignupNavigation()
        setupSigninButton()
    }

    private fun setupSigninButton() {
        button_signin.setOnClickListener {
            if ( isFormValid() ){
                signIn()
            }
        }
    }

    private fun signIn() {
        val username = edittext_username.text.toString()
        val password = edittext_password.text.toString()
        val signinProcessToObserve = signinViewModel.getUserByUsernameAndPassword(username, password)
        observeSigninProcess(signinProcessToObserve)
    }

    private fun observeSigninProcess(signinProcessToObserve: MutableLiveData<Result<Users>>) {
        signinProcessToObserve.observe(viewLifecycleOwner, Observer { signinResult ->
            when ( signinResult ){
                is Result.Success -> {
                    onSigninSuccess()
                }
                is Result.Loading<*> -> {
                    showSigninLoading()
                }
                is Result.Error -> {
                    onSigninError(signinResult.exception)
                }
            }
        })
    }

    private fun onSigninError(exception: Exception) {
        showSigninNotLoading()
        when ( exception.message.toString() ){
            ERROR_WRONG_PASSWORD -> {
                edittext_password.error = getString(R.string.error_entered_wrong_password)
            }
            ERROR_USERNAME_NOT_FOUND -> {
                edittext_username.error = getString(R.string.error_username_not_registered)
            }
            else -> {
                Toast.makeText(context, R.string.error_unknown_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSigninSuccess() {
        showSigninNotLoading()
        val action = SignInFragmentDirections.actionToMainFragment()
        findNavController().navigate(action)
    }

    private fun showSigninLoading() {
        progressbar_signin.visibility = View.VISIBLE
        button_signin.isEnabled = false
    }

    private fun showSigninNotLoading() {
        progressbar_signin.visibility = View.GONE
        button_signin.isEnabled = true
    }

    private fun isFormValid(): Boolean {
        if ( edittext_username.text.toString().isEmpty() ){
            edittext_username.error = getString(R.string.error_username_empty)
            return false
        }

        if ( edittext_password.text.toString().isEmpty() ){
            edittext_password.error = getString(R.string.error_username_empty)
            return false
        }

        return true
    }

    private fun setupToSignupNavigation() {
        textview_to_signup.setOnClickListener {
            val action = SignInFragmentDirections.actionSignupAccount()
            findNavController().navigate(action)
        }
    }


}