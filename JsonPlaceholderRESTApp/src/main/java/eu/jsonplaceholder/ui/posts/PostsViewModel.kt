package eu.jsonplaceholder.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.jsonplaceholder.model.LoadStateObject
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.repository.PostsRepository
import javax.inject.Inject

class PostsViewModel  @Inject constructor(private val repository: PostsRepository) : ViewModel() {
    //TODO not mutbable
    val posts: MutableLiveData<LoadStateObject<List<Post>>> = MutableLiveData()


    fun load() {
        posts.value = repository.loadPosts()
    }
}