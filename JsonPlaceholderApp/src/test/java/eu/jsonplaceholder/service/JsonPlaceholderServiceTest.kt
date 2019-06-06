package eu.jsonplaceholder.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JsonPlaceholderServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var jasonPlaceholderService: JsonPlaceholderService

    private lateinit var mockedServer: MockWebServer


    @Before
    fun initCoroutinesDispatcher(){
        Dispatchers.setMain(testDispatcher)
    }
    @Before
    fun initService() {
        mockedServer = MockWebServer()
        jasonPlaceholderService = Retrofit.Builder()
            .baseUrl(mockedServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(JsonPlaceholderService::class.java)
    }

    @After
    fun restoreCoroutinesDispatcher(){
        Dispatchers.resetMain()
    }
    @After
    fun shutdownServer() {
        mockedServer.shutdown()
    }

    @Test
    fun getPosts() {
        mockedServer.enqueue(prepareResponse("posts.json"))
        TestCoroutineScope().launch {
            val posts = jasonPlaceholderService.getPostsAsync().await().body()

            assertThat(posts, notNullValue())

            assertThat(posts!!.size,`is`(5))

            assertThat(posts[0].id,`is`(1L))
            assertThat(posts[1].id,`is`(2L))


            val request = mockedServer.takeRequest()
            assertThat(request.path,`is`("posts"))
        }
    }

    @Test
    fun getUser(){
        mockedServer.enqueue(prepareResponse("user_leanne_graham.json"))

        TestCoroutineScope().launch {
            val user = jasonPlaceholderService.getUserAsync(1).await().body()

            assertThat(user, notNullValue())

            assertThat(user!!.username,`is`("Bret"))

            assertThat(user.email,`is`("Sincere@april.biz"))
            assertThat(user!!.company.name,`is`("Romaguera-Crona"))


            val request = mockedServer.takeRequest()
            assertThat(request.path,`is`("users/1"))
        }
    }

    private fun prepareResponse(fileName: String): MockResponse {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("jsonPlaceholderService-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        return MockResponse().also { it.setBody(source.readString(Charsets.UTF_8)) }
    }
}