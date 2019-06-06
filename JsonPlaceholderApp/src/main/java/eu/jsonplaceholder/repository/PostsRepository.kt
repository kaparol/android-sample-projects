package eu.jsonplaceholder.repository

import androidx.lifecycle.LiveData
import eu.jsonplaceholder.db.CommentDao
import eu.jsonplaceholder.db.PostDao
import eu.jsonplaceholder.db.UserDao
import eu.jsonplaceholder.model.Comment
import eu.jsonplaceholder.model.LoadStateObject
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.model.User
import eu.jsonplaceholder.service.JsonPlaceholderService
import eu.jsonplaceholder.utils.DispatcherProvider
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val jsonPlaceholderService: JsonPlaceholderService,
    private val postsDao: PostDao,
    private val userDao: UserDao,
    private val commentDao: CommentDao,
    private val dispatcherProvider: DispatcherProvider
) {


    fun loadPosts(): LiveData<LoadStateObject<List<Post>>> {

        return object : ResourceObject<List<Post>>(dispatcherProvider) {
            override fun loadFromCache(): LiveData<List<Post>> {
                return postsDao.getAll()
            }

            override fun cacheResponse(posts: List<Post>) {

                postsDao.insert(posts)
            }

            override fun createRequestAsync(): Deferred<Response<List<Post>>> {
                return jsonPlaceholderService.getPostsAsync()
            }

        }.toLiveData()
    }

    fun loadPost(postId: Long): LiveData<LoadStateObject<Post>> {

        return object : ResourceObject<Post>(dispatcherProvider) {
            override fun loadFromCache() = postsDao.getById(postId)

            override fun cacheResponse(post: Post) = postsDao.insert(post)

            override fun createRequestAsync() = jsonPlaceholderService.getPostAsync(postId)

        }.toLiveData()
    }

    fun loadUser(userId: Long): LiveData<LoadStateObject<User>> {
        return object : ResourceObject<User>(dispatcherProvider) {
            override fun loadFromCache() = userDao.findById(userId)

            override fun cacheResponse(user: User) = userDao.insert(user)

            override fun createRequestAsync() = jsonPlaceholderService.getUserAsync(userId)
        }.toLiveData()
    }

    fun loadUsers(): LiveData<LoadStateObject<List<User>>> {
        return object : ResourceObject<List<User>>(dispatcherProvider) {
            override fun loadFromCache() = userDao.getAll()

            override fun cacheResponse(users: List<User>) = userDao.insert(users)

            override fun createRequestAsync() = jsonPlaceholderService.getUsersAsync()

        }.toLiveData()
    }


    fun loadCommentsByPost(postId: Long): LiveData<LoadStateObject<List<Comment>>> {
        return object : ResourceObject<List<Comment>>(dispatcherProvider) {

            override fun loadFromCache() = commentDao.getAll()

            override fun cacheResponse(users: List<Comment>) = commentDao.insert(users)

            override fun createRequestAsync(): Deferred<Response<List<Comment>>> {
                return jsonPlaceholderService.getCommentsAsync(postId)
            }

        }.toLiveData()
    }
}