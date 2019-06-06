package eu.jsonplaceholder.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.jsonplaceholder.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<User>)

    @Query("SELECT * FROM user WHERE username = :username")
    fun findByUsername(username: String): LiveData<User>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun findById(userId: Long): LiveData<User>

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>
}