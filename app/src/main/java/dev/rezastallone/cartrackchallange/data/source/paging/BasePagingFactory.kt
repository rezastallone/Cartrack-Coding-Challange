package source.paging

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import dev.rezastallone.cartrackchallange.constant.VALID_LOCAL_OFFSET
import dev.rezastallone.cartrackchallange.data.Result
import dev.rezastallone.cartrackchallange.data.source.PreferenceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

abstract class BasePagingFactory<LocalType : Any, RemoteType>(val limit: Int, tag: String) :
    PageKeyedDataSource<Int, LocalType>(),
    KoinComponent,
    CoroutineScope {

    val tag = tag.replace("/","")
    val context: Context by inject()
    val networkState = MutableLiveData<Result<List<LocalType>>>()
    val initialLoad = MutableLiveData<Result<List<LocalType>>>()

    private fun getValidLocalOffset(): Int {
        return PreferenceHelper.getIntPreference("$tag$VALID_LOCAL_OFFSET",0,context)
    }

    private fun setValidLocalOffset(value: Int) {
        PreferenceHelper.putInt("$tag$VALID_LOCAL_OFFSET", value,context)
    }

    override val coroutineContext: CoroutineContext
        get() = CoroutineScope(Dispatchers.IO).coroutineContext


    override fun loadInitial(initialParams: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, LocalType>) {
        initialLoad.postValue(Result.Loading(Any()))
        load(0, limit) { result, newOffset ->
            initialLoad.postValue(Result.Success(result))
            callback.onResult(result, 0, newOffset)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, LocalType>) {
        load(params.key, limit) { result, nextKey ->
            callback.onResult(result, nextKey)
        }
    }

    // no need to load before because user can only pull refresh from top
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, LocalType>) {
    }

    private fun load(offset: Int, limit: Int, onSuccess: (List<LocalType>, Int) -> Unit) {
        val result = runBlocking {
            if (offsetBeyondCache(offset)) {
                requestFreshRemoteData(offset, limit)
            } else {
                getDataFromLocal(offset, limit)
            }
        }

        onSuccess(result, offset + result.size)
    }

    private suspend fun requestFreshRemoteData(
        offset: Int,
        limit: Int
    ): MutableList<LocalType> {
        networkState.postValue(Result.Loading(Any()))
        val response = getDataFromRemote(offset, limit).await()
        val body = response.body()
        return if (response.isSuccessful) {
            val data = if (body != null) {
                saveRemoteData(body)
                val data = remoteToLocaltype(body)
                setValidLocalOffset(offset + data.size)
                data
            } else {
                getDataFromLocal(offset, limit)
            }
            networkState.postValue(Result.Success(data))
            data
        } else {
            response.errorBody()?.let {
                networkState.postValue(Result.Error(Exception(it.string())))
            }
            getDataFromLocal(offset, limit)
        }
    }

    private fun offsetBeyondCache(offset: Int) = getValidLocalOffset() <= offset

    fun refresh(){
        setValidLocalOffset(0)
        invalidate()
    }

    abstract fun remoteToLocaltype(remoteType: RemoteType): MutableList<LocalType>

    abstract fun getDataFromRemote(skip: Int, limit: Int): Deferred<Response<RemoteType>>

    abstract fun getDataFromLocal(skip: Int, limit: Int): MutableList<LocalType>

    abstract fun saveRemoteData(data: RemoteType)
}