package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.data.Contact

interface ContactsDataSource {
    fun getContacts(skip: Int, limit: Int) : MutableList<Contact>
    fun insertContact(data: List<Contact>)
}