package dev.rezastallone.cartrackchallange.data.source.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.source.local.datasource.ContactsDataSource
import dev.rezastallone.cartrackchallange.data.source.remote.RemoteClient
import kotlinx.coroutines.Deferred
import retrofit2.Response
import source.paging.BasePagingFactory

class ContactPagingFactory(
    val remote: RemoteClient,
    val dataSource: ContactsDataSource
) : DataSource.Factory<Int, Contact>() {

    val sourceLiveData = MutableLiveData<BasePagingFactory<Contact, List<Contact>>>()

    override fun create(): DataSource<Int, Contact> {
        val source =
            object : BasePagingFactory<Contact, List<Contact>>(5, "userlist") {

                override fun remoteToLocaltype(remoteType: List<Contact>): MutableList<Contact> {
                    return remoteType.toMutableList()
                }

                override fun getDataFromRemote(
                    skip: Int,
                    limit: Int
                ): Deferred<Response<List<Contact>>> {
                    return remote.getUsers()
                }

                override fun getDataFromLocal(skip: Int, limit: Int): MutableList<Contact> {
                    return dataSource.getContacts(skip, limit)
                }

                override fun saveRemoteData(data: List<Contact>) {
                    dataSource.insertContact(data)
                }
            }
        sourceLiveData.postValue(source)
        return source
    }
}