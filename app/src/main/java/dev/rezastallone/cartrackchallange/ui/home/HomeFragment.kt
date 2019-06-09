package dev.rezastallone.cartrackchallange.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.R.layout
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(){

    private lateinit var contactAdapter: ContactListAdapter
    val homeViewModel : HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupContactList()
        observeContactList()
    }

    private fun observeContactList() {
        homeViewModel.contactInteractor.pagedList.observe(viewLifecycleOwner, Observer { contactPagedList ->
            contactAdapter.submitList(contactPagedList)
        })
    }

    private fun setupContactList() {
        contactAdapter = ContactListAdapter()
        val itemDecor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerview_contact.addItemDecoration(itemDecor)
        recyclerview_contact.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerview_contact.adapter = contactAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.title_home)
    }
}