package eu.jsonplaceholder.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider{

    val main: CoroutineDispatcher

    val background: CoroutineDispatcher
}