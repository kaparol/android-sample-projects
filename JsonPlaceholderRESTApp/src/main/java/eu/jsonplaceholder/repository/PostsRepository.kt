package eu.jsonplaceholder.repository

import eu.jsonplaceholder.model.LoadState
import eu.jsonplaceholder.model.LoadStateObject
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.service.JsonPlaceholderService
import javax.inject.Inject

class PostsRepository @Inject constructor(private val jsonPlaceholderService: JsonPlaceholderService){
    fun loadPosts(): LoadStateObject<List<Post>>? {
       val posts = mutableListOf<Post>()
        for(i: Long in 1L..1000L) {
            posts.add(Post(i, i, "Fake News $i", "QQQQQQQQQ QQQ QQQ "))
        }

       return LoadStateObject(LoadState.SUCCESS,posts,null)
    }
}