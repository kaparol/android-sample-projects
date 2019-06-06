package eu.jsonplaceholder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.jsonplaceholder.model.Comment
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.model.User

@Database(entities = [User::class, Post::class, Comment::class], version = 1)
abstract class PlaceholderDb: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}