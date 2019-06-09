package dev.rezastallone.cartrackchallange.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.constant.EXTRA_CONTACT
import dev.rezastallone.cartrackchallange.data.Contact
import kotlinx.android.synthetic.main.content_contact_address.*
import kotlinx.android.synthetic.main.content_contact_information.*

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in an independent fragment
 * in two-pane mode (on tablets) or a on handsets.
 */
class ContactDetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideTransitionAnimation()
        getContactExtra()
    }

    private fun overrideTransitionAnimation() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun getContactExtra() {
        arguments?.let {
            if (it.containsKey(EXTRA_CONTACT)) {
                contact = it.getParcelable(EXTRA_CONTACT) as Contact
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupContactInfo()
        setupContactAddress()
        SetupToolbar()
    }

    private fun SetupToolbar() {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = contact.name
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupContactAddress() {
        textview_address.text = contact.getFullAddress()
    }

    private fun setupContactInfo() {
        contact.run {
            textview_name.text = name
            textview_username.text = username
            textview_email.text = email
            textview_phone.text = phone
            textview_website.text = website
            textview_company_name.text = company.name
            textview_company_catchphrase.text = company.catchPhrase
            textview_company_bs.text = company.bs
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
        setupMapMarker()
    }

    private fun setupMapMarker() {
        val lat = contact.address.geo.lat.toDouble()
        val lng = contact.address.geo.lng.toDouble()
        val contactPosition = LatLng(lat, lng)
        val markerOption = MarkerOptions().apply {
            position(contactPosition)
            title(contact.getFullAddress())
        }
        googleMap.addMarker(markerOption)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(contactPosition))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null);
    }
}
