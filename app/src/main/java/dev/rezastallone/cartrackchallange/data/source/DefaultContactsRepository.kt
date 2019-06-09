package dev.rezastallone.cartrackchallange.data.source

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.source.local.datasource.ContactsDataSource
import dev.rezastallone.cartrackchallange.data.source.paging.ContactPagingFactory
import dev.rezastallone.cartrackchallange.data.source.remote.RemoteClient
import source.paging.BasePagingFactory

class DefaultContactsRepository(val contactsDataSource: ContactsDataSource, val remoteClient: RemoteClient) :
    ContactsRepository {

    private lateinit var personalDataSource: MutableLiveData<BasePagingFactory<Contact, List<Contact>>>

    override fun loadContacts(): PagedListInteractor<Contact> {
        val sourceFactory = ContactPagingFactory(remoteClient, contactsDataSource)

        personalDataSource = sourceFactory.sourceLiveData

        val personalThreadLiveData = LivePagedListBuilder(sourceFactory, 5)
            .build()

        return PagedListInteractor<Contact>(
            pagedList = personalThreadLiveData,
            refresh = {
                sourceFactory.sourceLiveData.value?.refresh()
            },
            refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.initialLoad
            }
        )
    }
}