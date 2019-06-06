package eu.jsonplaceholder.service

import eu.jsonplaceholder.model.Comment
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException

interface JsonPlaceholderService {

    @Throws(IOException::class)
    @GET("posts")
    fun getPostsAsync(): Deferred<Response<List<Post>>>

    @GET("posts/{postId}")
    fun getPostAsync(@Path("postId") postId: Long): Deferred<Response<Post>>

    @GET("users")
    fun getUsersAsync(): Deferred<Response<List<User>>>

    @GET("users/{id}")
    fun getUserAsync(@Path("id") userId:Long): Deferred<Response<User>>

    @GET("comments")
    fun getCommentsAsync(@Query("postId") postId:Long): Deferred<Response<List<Comment>>>
}