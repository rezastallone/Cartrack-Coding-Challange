package dev.rezastallone.cartrackchallange

import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.source.local.datasource.ContactsDataSource

class FakeContactsLocalDataSource : ContactsDataSource{

    private val contactData = mutableListOf<Contact>()

    override fun getContacts(skip: Int, limit: Int): MutableList<Contact> {
        return contactData.subList(skip, skip + limit)
    }

    override fun insertContact(data: List<Contact>) {
        contactData.addAll(data)
    }

}