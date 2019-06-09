package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.data.Contact

interface ContactsDataSource {
    fun getContacts() : List<Contact>
}