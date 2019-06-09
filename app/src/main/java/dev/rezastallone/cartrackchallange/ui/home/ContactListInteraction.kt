package dev.rezastallone.cartrackchallange.ui.home

import android.view.View
import dev.rezastallone.cartrackchallange.data.Contact

interface ContactListInteraction {
    fun openDetail(contact: Contact, itemView: View)
}