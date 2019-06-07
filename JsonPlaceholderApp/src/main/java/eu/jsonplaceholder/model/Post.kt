package eu.jsonplaceholder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey val id: Long,
    val userId: Long,
    val title: String,
    val body: String
) {
    fun getImageUrl(): String  =  "http://via.placeholder.com/500x250/${userId}96f.png/02$userId"
}