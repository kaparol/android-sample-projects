package eu.jsonplaceholder.model

data class LoadStateObject<out T> (val state: LoadState, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): LoadStateObject<T> {
            return LoadStateObject(LoadState.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): LoadStateObject<T> {
            return LoadStateObject(LoadState.ERROR, data, msg)
        }

        fun <T> loading(data: T?): LoadStateObject<T> {
            return LoadStateObject(LoadState.LOADING, data, null)
        }
    }
}