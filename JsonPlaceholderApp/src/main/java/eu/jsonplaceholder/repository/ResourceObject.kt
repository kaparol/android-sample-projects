package eu.jsonplaceholder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import eu.jsonplaceholder.model.LoadStateObject
import eu.jsonplaceholder.utils.DispatcherProvider
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException


abstract class ResourceObject<ResultType>(private val dispatcherProvider: DispatcherProvider) {
    private val result = MediatorLiveData<LoadStateObject<ResultType>>()

    init {
        result.value = LoadStateObject.loading(null)
        fetch()
    }

    private fun fetch() {
        val job = SupervisorJob()
        CoroutineScope(dispatcherProvider.main + job).launch {
            try {
                val serviceResponse = createRequestAsync().await()
                if (serviceResponse.isSuccessful && serviceResponse.body() != null) {
                    val responseBody = serviceResponse.body()
                    responseBody?.let {
                        withContext(dispatcherProvider.background) { cacheResponse(responseBody) }
                    }
                    val fromCache = withContext(dispatcherProvider.background) { loadFromCache() }
                    result.addSource(fromCache) {
                        result.value = LoadStateObject.success(it)
                        result.removeSource(fromCache)
                    }
                } else {
                    result.value = LoadStateObject.error(serviceResponse.message() ?: "", null)
                }
            }catch (ex: Throwable){
                result.value = LoadStateObject.error(ex.message ?: "", null)
            }
        }
    }

    abstract fun loadFromCache(): LiveData<ResultType>

    abstract fun cacheResponse(result: ResultType)

    @Throws(IOException::class)
    abstract fun createRequestAsync(): Deferred<Response<ResultType>>

    fun toLiveData() = result as LiveData<LoadStateObject<ResultType>>
}