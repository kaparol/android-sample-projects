package eu.jsonplaceholder.ui.posts

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import eu.jsonplaceholder.model.LoadStateObject
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.repository.PostsRepository
import javax.inject.Inject

class PostsViewModel @Inject constructor(private val repository: PostsRepository) : ViewModel() {

    val posts: MediatorLiveData <LoadStateObject<List<Post>>> = MediatorLiveData ()

    fun load() {
        posts.addSource(repository.loadPosts()) { loadStateObject ->
            posts.value = loadStateObject
        }
    }
}