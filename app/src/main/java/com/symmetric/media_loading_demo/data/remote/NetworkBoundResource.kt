package com.symmetric.media_loading_demo.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.symmetric.media_loading_demo.AppExecutors
import com.symmetric.media_loading_demo.data.model.api.ApiEmptyResponse
import com.symmetric.media_loading_demo.data.model.api.ApiErrorResponse
import com.symmetric.media_loading_demo.data.model.api.ApiResponse
import com.symmetric.media_loading_demo.data.model.api.ApiSuccessResponse

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
* Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */

//TODO to integrate pagination behaviour implement the PagedList.BoundaryCallback methods
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) :
    PagedList.BoundaryCallback<ResultType>() {


    private var pageCount = 1
    private val result = MediatorLiveData<Resource<PagedList<ResultType>>>()
    private var dbSource: LiveData<PagedList<ResultType>>

    val pagingConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(50)
        .build()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val temp = loadFromDb()
        dbSource = createPagingList(temp)
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(pageCount)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun createPagingList(temp: DataSource.Factory<Int, ResultType>): LiveData<PagedList<ResultType>> {
        return LivePagedListBuilder(temp, pagingConfig)
            .setBoundaryCallback(this)
            .setInitialLoadKey(1)
            .build()
    }

    @MainThread
    private fun setValue(newValue: Resource<PagedList<ResultType>>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(pageNumber: Int) {
        val apiResponse = createCall(pageNumber)
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    pageCount++
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(dbSource) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<PagedList<ResultType>>>

    @WorkerThread
    open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    open fun shouldFetch(data: PagedList<ResultType>): Boolean = true

    @MainThread
    protected abstract fun loadFromDb(): DataSource.Factory<Int, ResultType>

    @MainThread
    protected abstract fun createCall(pageNumber: Int): LiveData<ApiResponse<RequestType>>
}

