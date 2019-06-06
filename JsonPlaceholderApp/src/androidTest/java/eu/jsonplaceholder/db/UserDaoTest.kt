package eu.jsonplaceholder.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eu.jsonplaceholder.util.LiveDataTestUtil
import eu.jsonplaceholder.util.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test


class UserDaoTest : DbTest() {

    companion object {
        const val userLoginJK = "john.kowalsky"
        const val userLoginAN = "adam.nowak"
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun shouldInsertAndLoadUserCorrectly() {
        val user = TestUtil.createUser(0, userLoginJK)
        db.userDao().insert(user)

        val loaded = LiveDataTestUtil.getValue(db.userDao().findByUsername(user.username))
        MatcherAssert.assertThat(loaded.username, CoreMatchers.`is`(userLoginJK))

        val replacement = TestUtil.createUser(1, userLoginAN)
        db.userDao().insert(replacement)

        val loadedReplacement = LiveDataTestUtil.getValue(db.userDao().findById(replacement.id))
        MatcherAssert.assertThat(loadedReplacement.username, CoreMatchers.`is`(userLoginAN))
    }
}