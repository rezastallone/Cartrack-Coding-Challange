package dev.rezastallone.cartrackchallange.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.R.layout
import dev.rezastallone.cartrackchallange.constant.EXTRA_CONTACT
import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.ui.contact.ContactDetailFragment
import kotlinx.android.synthetic.main.contact_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var contactAdapter: ContactListAdapter
    val homeViewModel: HomeViewModel by viewModel()

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        configurePaneForContactDetail()
        setupAdapter()
        setupContactList()
        observeContactList()
    }

    private fun configurePaneForContactDetail() {
        if (contact_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
    }

    private fun observeContactList() {
        homeViewModel.contactInteractor.pagedList.observe(viewLifecycleOwner, Observer { contactPagedList ->
            contactAdapter.submitList(contactPagedList)
        })
    }

    private fun setupAdapter() {
        contactAdapter = ContactListAdapter(
            object : ContactListInteraction {
                override fun openDetail(contact: Contact) {
                    openContactDetail(contact)
                }
            }
        )
    }

    private fun openContactDetail(contact: Contact) {
        if (twoPane) {
            val fragment = ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_CONTACT, contact)
                }
            }
            childFragmentManager
                .beginTransaction()
                .replace(R.id.contact_detail_container, fragment)
                .commit()
        } else {
            val action = HomeFragmentDirections.actionToContactDetail(contact)
            findNavController().navigate(action)
        }
    }

    private fun setupContactList() {
        val itemDecor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        contact_list.addItemDecoration(itemDecor)
        contact_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        contact_list.adapter = contactAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.title_home)
    }
}