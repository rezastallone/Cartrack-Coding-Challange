package dev.rezastallone.cartrackchallange.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.rezastallone.cartrackchallange.R
import kotlinx.android.synthetic.main.signin_fragment.*

class SignInFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview_to_signup.setOnClickListener {
            val action = SignInFragmentDirections.actionSignupAccount()
            findNavController().navigate(action)
        }
    }

}