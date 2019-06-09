package dev.rezastallone.cartrackchallange.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import dev.rezastallone.cartrackchallange.R
import dev.rezastallone.cartrackchallange.data.Contact

class ContactListAdapter(private val interaction: ContactListInteraction) : PagedListAdapter<Contact, ContactViewHolder>(ContactDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contact_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        contact?.let { holder.bind(it, interaction) }
    }

}