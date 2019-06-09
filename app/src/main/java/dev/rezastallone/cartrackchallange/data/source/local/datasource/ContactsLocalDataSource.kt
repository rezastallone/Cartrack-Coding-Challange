package dev.rezastallone.cartrackchallange.data.source.local.datasource

import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.source.local.AppDatabase

class ContactsLocalDataSource(db: AppDatabase) : ContactsDataSource {
    private val contactDao = db.contactsDao()

    override fun getContacts(offset: Int, limit: Int): MutableList<Contact> {
        return contactDao.getContacts(limit, offset)
    }

    override fun insertContact(data: List<Contact>) {
        return contactDao.insert(data)
    }
}