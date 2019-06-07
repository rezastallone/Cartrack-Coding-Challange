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

class SignupFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToSigninNavigation()
        setupCountryListSpinner()
    }

    private fun setupCountryListSpinner() {
        val spinnerCountryAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        )
        spinnerCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_country.adapter = spinnerCountryAdapter
        spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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