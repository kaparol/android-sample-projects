package eu.jsonplaceholder.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eu.jsonplaceholder.util.LiveDataTestUtil.getValue
import eu.jsonplaceholder.util.TestUtil
import eu.jsonplaceholder.util.TestUtil.body
import eu.jsonplaceholder.util.TestUtil.postId
import eu.jsonplaceholder.util.TestUtil.title
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class PostDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun shouldInsertAndLoadPostCorrectly() {
        //given
        val post1 = TestUtil.createPost(postId, 1)

        db.postDao().insert(post1)
        val loadedPost = getValue(db.postDao().getById(1))

        assertThat(loadedPost, notNullValue())
        assertThat(loadedPost.id, `is`(postId))
        assertThat(loadedPost.title, `is`(title))
        assertThat(loadedPost.body, `is`(body))
        assertThat(loadedPost.userId, notNullValue())
    }

    @Test
    fun shouldInsertAndLoadPostCollectionCorrectly() {
        //given
        val post1 = TestUtil.createPost(postId, 1)
        val post2 = TestUtil.createPost(postId + 1, 1)
        val post3 = TestUtil.createPost(postId + 2, 1)
        val post4 = TestUtil.createPost(postId + 3, 1)


        db.postDao().insert(listOf(post1, post2, post3, post4))
        val loadedPosts = getValue(db.postDao().getAll())


        assertThat(loadedPosts.size, `is`(4))
        assertThat(loadedPosts.map { post -> post.id }, `is`(listOf<Long>(postId, postId + 1, postId + 2, postId + 3)))
    }
}

