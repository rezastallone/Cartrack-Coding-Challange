package dev.rezastallone.cartrackchallange.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.rezastallone.cartrackchallange.data.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view){
    fun bind(
        contact: Contact,
        interaction: ContactListInteraction
    ) {
        with(itemView){
            textView_username.text = contact.username
        }

        setupClick(contact, interaction)

    }

    private fun setupClick(contact: Contact, interaction: ContactListInteraction){
        itemView.setOnClickListener {
            interaction.openDetail(contact)
        }
    }

}