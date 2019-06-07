package eu.jsonplaceholder.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.jsonplaceholder.model.Comment


@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comments: List<Comment>)

    @Query("SELECT * FROM comment WHERE postId = :postId")
    fun getByPostId(postId: Long): LiveData<List<Comment>>

    @Query("SELECT * FROM comment")
    fun getAll(): LiveData<List<Comment>>

}