package dev.rezastallone.cartrackchallange.data.source

import dev.rezastallone.cartrackchallange.data.Contact

interface ContactsRepository {
    fun loadContacts(): PagedListInteractor<Contact>
}