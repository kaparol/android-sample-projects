package eu.jsonplaceholder.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.jsonplaceholder.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Post>)

    @Query("SELECT * FROM Post WHERE id = :id")
    fun getById(id: Long): LiveData<Post>

    @Query ("SELECT * FROM Post")
    fun getAll(): LiveData<List<Post>>
}