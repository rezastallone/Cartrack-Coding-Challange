package dev.rezastallone.cartrackchallange.ui.home

import androidx.recyclerview.widget.DiffUtil
import dev.rezastallone.cartrackchallange.data.Contact

class ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.username == newItem.username
    }

}