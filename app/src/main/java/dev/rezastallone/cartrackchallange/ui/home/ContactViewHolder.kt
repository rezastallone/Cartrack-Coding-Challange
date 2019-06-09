package dev.rezastallone.cartrackchallange.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.rezastallone.cartrackchallange.data.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view){
    fun bind(contact: Contact) {
        itemView.textView_username.text = contact.username
    }

}