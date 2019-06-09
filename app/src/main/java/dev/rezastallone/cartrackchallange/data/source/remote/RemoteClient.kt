package dev.rezastallone.cartrackchallange.data.source.remote

import dev.rezastallone.cartrackchallange.constant.USERS_ENDPOIINT
import dev.rezastallone.cartrackchallange.data.Contact
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET


interface RemoteClient{
    @GET(USERS_ENDPOIINT)
    fun getUsers() : Deferred<Response<List<Contact>>>
}