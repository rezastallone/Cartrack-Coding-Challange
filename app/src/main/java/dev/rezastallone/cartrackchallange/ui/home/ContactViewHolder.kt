package dev.rezastallone.cartrackchallange.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.rezastallone.cartrackchallange.data.Contact
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    init {
        initMapView()
    }

    private fun initMapView() {
        itemView.mapview_contact.onCreate(null)
        itemView.mapview_contact.getMapAsync(this)
    }

    fun bind(
        contact: Contact,
        interaction: ContactListInteraction
    ) {
        bindData(contact)
        setupClick(contact, interaction)
        itemView.mapview_contact.tag = contact
        if ( googleMap != null ){
            setupMapMarker(contact)
        }
    }

    private fun bindData(contact: Contact) {
        with(itemView) {
            contact.run {
                textview_name.text = name
                textview_username.text = username
                textview_email.text = email
                textview_phone.text = phone
                textview_address.text = getFullAddress()
            }
        }
    }

    private fun setupClick(contact: Contact, interaction: ContactListInteraction){
        itemView.setOnClickListener {
            interaction.openDetail(contact, itemView)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        MapsInitializer.initialize(itemView.context)
        googleMap = map!!
        val contact = itemView.tag
        contact?.let {
            setupMapMarker(it as Contact)
        }
    }

    private fun setupMapMarker(contact: Contact) {
        val lat = contact.address.geo.lat.toDouble()
        val lng = contact.address.geo.lng.toDouble()
        val contactPosition = LatLng(lat, lng)
        val markerOption = MarkerOptions().apply {
            position(contactPosition)
            title(contact.getFullAddress())
        }
        googleMap?.run {
            addMarker(markerOption)
            moveCamera(CameraUpdateFactory.newLatLng(contactPosition))
            animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null);
            mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }
}