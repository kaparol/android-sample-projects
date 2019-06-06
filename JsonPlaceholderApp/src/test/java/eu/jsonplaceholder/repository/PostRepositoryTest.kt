package eu.jsonplaceholder.repository


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import eu.jsonplaceholder.db.CommentDao
import eu.jsonplaceholder.db.PlaceholderDb
import eu.jsonplaceholder.db.PostDao
import eu.jsonplaceholder.db.UserDao
import eu.jsonplaceholder.model.LoadStateObject
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.service.JsonPlaceholderService
import eu.jsonplaceholder.util.TestDispatcherProvider
import eu.jsonplaceholder.util.TestUtil
import eu.jsonplaceholder.util.TestUtil.postId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

@RunWith(RobolectricTestRunner::class)
class PostRepositoryTest {

    companion object{
        private const val SERVICE_EXCEPTION_MESSAGE = "Something wrong happened"
    }

    private lateinit var repository: PostsRepository
    private lateinit var postDao: PostDao
    private lateinit var userDao: UserDao
    private lateinit var commentDao: CommentDao
    private val service = mock<JsonPlaceholderService>()
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()

    private val testDispatcher = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun initTest() {
        val database = Room.inMemoryDatabaseBuilder(context, PlaceholderDb::class.java)
            .allowMainThreadQueries().build()

        postDao = database.postDao()
        userDao = database.userDao()
        commentDao = database.commentDao()

        repository = PostsRepository(service, postDao, userDao, commentDao, TestDispatcherProvider(testDispatcher))
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldLoadPostsCorrectly() {
        val userId = postId
        val post = TestUtil.createPost(postId, userId)
        val postList = listOf(post)
        val dbPosts = MutableLiveData<List<Post>>()

        dbPosts.observeForever(mock())
        val postsResponse = Response.success(postList)
        `when`(service.getPostsAsync()).thenReturn(postsResponse.toDeferred())

        val posts = repository.loadPosts()
        val observer = mock<Observer<LoadStateObject<List<Post>>>>()
        posts.observeForever(observer)

        verify(service, times(1)).getPostsAsync()
        verifyNoMoreInteractions(service)

        verify(observer).onChanged(LoadStateObject.success(postList))
    }

    @Test
    fun shouldReturnErrorBecauseOfServiceException() {
        val userId = postId
        val dbPosts = MutableLiveData<List<Post>>()

        dbPosts.observeForever(mock())
        `when`(service.getPostsAsync()).thenThrow(IOException::class.java)

        val posts = repository.loadPosts()
        val observer = mock<Observer<LoadStateObject<List<Post>>>>()
        posts.observeForever(observer)

        verify(service, times(1)).getPostsAsync()
        verifyNoMoreInteractions(service)

        verify(observer).onChanged(LoadStateObject.error("",null))
    }


    inline fun <reified T> mock(): T = mock(T::class.java)

    fun <T> T.toDeferred() = GlobalScope.async { this@toDeferred }
}