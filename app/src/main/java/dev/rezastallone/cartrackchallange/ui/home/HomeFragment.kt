package dev.rezastallone.cartrackchallange.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.R.layout
import dev.rezastallone.cartrackchallange.constant.EXTRA_CONTACT
import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.ui.contact.ContactDetailFragment
import kotlinx.android.synthetic.main.contact_list.*
import kotlinx.android.synthetic.main.contact_list_item.view.*
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var contactAdapter: ContactListAdapter
    private val homeViewModel: HomeViewModel by viewModel()

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
        configureClientUseTablet()
        setupAdapter()
        setupContactList()
        observeContactList()
        observerContactLoadingState()
        setupPullRefresh()
    }

    private fun observerContactLoadingState() {
        homeViewModel.contactInteractor.refreshState.observe(viewLifecycleOwner, Observer { loadingResult ->
            when ( loadingResult ){
                is Result.Success, is Result.Error -> swipelayout_contact_list.isRefreshing = false
            }
        })
    }

    private fun setupPullRefresh() {
        swipelayout_contact_list.setOnRefreshListener {
            homeViewModel.refresh()
        }
    }

    private fun configureClientUseTablet() {
        if (clientInTablet()) {
            twoPane = true
        }
    }

    private fun clientInTablet() = contact_detail_container != null

    private fun observeContactList() {
        homeViewModel.contactInteractor.pagedList.observe(viewLifecycleOwner, Observer { contactPagedList ->
            if (  contactPagedList.isEmpty() ) {
                showContactEmpty()
            } else {
                showContactNotEmpty()
                contactAdapter.submitList(contactPagedList)
            }
        })
    }

    private fun showContactEmpty() {
        textview_title_empty_contacts.visibility = View.VISIBLE
        frameLayout.visibility = View.GONE
    }

    private fun showContactNotEmpty() {
        textview_title_empty_contacts.visibility = View.GONE
        frameLayout.visibility = View.VISIBLE
    }

    private fun setupAdapter() {
        contactAdapter = ContactListAdapter(
            object : ContactListInteraction {
                override fun openDetail(
                    contact: Contact,
                    itemView: View
                ) {
                    openContactDetail(contact, itemView)
                }
            }
        )
    }

    private fun openContactDetail(contact: Contact, itemView: View) {
        if (twoPane) {
            openContactDetailInTwoPane(contact)
        } else {
            navigateToContactDetail(contact, itemView)
        }
    }

    private fun navigateToContactDetail(
        contact: Contact,
        itemView: View
    ) {
        val extras = FragmentNavigatorExtras(
            itemView.textview_name to "name",
            itemView.textview_username to "username",
            itemView.textview_email to "email",
            itemView.textview_phone to "phone",
            itemView.textview_address to "address"
        )
        val action = HomeFragmentDirections.actionToContactDetail(contact)
        findNavController().navigate(action, extras)
    }

    private fun openContactDetailInTwoPane(contact: Contact) {
        val fragment = ContactDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_CONTACT, contact)
            }
        }
        childFragmentManager
            .beginTransaction()
            .replace(R.id.contact_detail_container, fragment)
            .commit()
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