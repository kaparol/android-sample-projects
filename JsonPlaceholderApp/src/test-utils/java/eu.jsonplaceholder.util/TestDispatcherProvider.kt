package eu.jsonplaceholder.util

import eu.jsonplaceholder.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatcherProvider(testDispatcher: TestCoroutineDispatcher) : DispatcherProvider {

    override val main: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    override val background: CoroutineDispatcher
        get() = Dispatchers.Unconfined

}