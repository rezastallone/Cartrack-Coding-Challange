package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase

class ContactsLocalDataSource(db: AppDatabase) : ContactsDataSource {
    private val contactDao = db.contactsDao()
    override fun getContacts(): List<Contact> {
        return contactDao.getContacts()
    }

}