package dev.rezastallone.cartrackchallange.ui.home

import androidx.lifecycle.ViewModel
import dev.rezastallone.cartrackchallange.data.source.ContactsRepository

class HomeViewModel(contactsRepository: ContactsRepository) :ViewModel(){

    val contactInteractor = contactsRepository.loadContacts()

    fun refresh() {
        contactInteractor.refresh()
    }
}