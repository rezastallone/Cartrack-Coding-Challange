package dev.rezastallone.cartrackchallange.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import dev.rezastallone.cartrackchallange.data.Result

data class PagedListInteractor<T : Any>(
    val pagedList: LiveData<PagedList<T>>,
    val refreshState: LiveData<Result<Any>>,
    val refresh: () -> Unit
)