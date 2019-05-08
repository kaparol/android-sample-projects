package eu.jsonplaceholder.service

import androidx.lifecycle.LiveData
import eu.jsonplaceholder.model.Comment
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.model.User
import retrofit2.http.GET

interface JsonPlaceholderService {

    @GET("posts")
    fun getPosts(): LiveData<List<Post>>

    @GET("users")
    fun getUsers(): LiveData<List<User>>

    @GET("comments")
    fun getComments(): LiveData<List<Comment>>
}