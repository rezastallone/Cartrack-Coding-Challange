package dev.rezastallone.cartrackchallange.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.constant.ERROR_USERNAME_NOT_FOUND
import dev.rezastallone.cartrackchallange.constant.ERROR_WRONG_PASSWORD
import dev.rezastallone.cartrackchallange.constant.PREF_SIGNEDIN_USERNAME
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.Users
import dev.rezastallone.cartrackchallange.data.source.PreferenceHelper
import kotlinx.android.synthetic.main.signin_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(){

    private val signinViewModel: SigninViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkSignedinUser()
    }

    private fun checkSignedinUser() {
        if ( isUserSignedInExist() ){
            navigateToHome()
        }
    }

    /***
     * Not puting shared preference in view model to avoid exposing view model to android context
     * and avoid leaking it
     */

    private fun isUserSignedInExist(): Boolean {
        val context = context
        return context != null && PreferenceHelper.getString(PREF_SIGNEDIN_USERNAME, "", context).isNotEmpty()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToSignupNavigation()
        setupSigninButton()
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.title_signin)
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
                    onSigninSuccess(signinResult.data)
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

    private fun onSigninSuccess(signedinUser: Users) {
        showSigninNotLoading()
        saveSignedinUser(signedinUser)
        navigateToHome()
    }

    /***
     * Saving username not perfomed in view model because it expose view model to android context
     * and may leak it
     */

    private fun saveSignedinUser(signedinUser: Users) {
        context?.let { PreferenceHelper.putString(PREF_SIGNEDIN_USERNAME, signedinUser.username, it) }
    }

    private fun navigateToHome() {
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