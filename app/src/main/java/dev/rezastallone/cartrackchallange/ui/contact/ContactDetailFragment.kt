package dev.rezastallone.cartrackchallange.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.constant.EXTRA_CONTACT
import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.ui.contact.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.contact_detail.view.*

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a [ContactListActivity]
 * in two-pane mode (on tablets) or a [ContactDetailActivity]
 * on handsets.
 */
class ContactDetailFragment : Fragment() {

    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val rootView = inflater.inflate(R.layout.contact_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, contact.name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
