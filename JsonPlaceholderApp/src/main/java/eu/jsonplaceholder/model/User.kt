package eu.jsonplaceholder.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey val id: Long,
    val name: String,
    val username: String,
    val email: String,
    @field:SerializedName("address")
    @field:Embedded(prefix = "address_")
    val address: Address,
    val phone: String,
    val website: String,
    @field:SerializedName("company")
    @field:Embedded(prefix = "company_")
    val company: Company
) {
    companion object {
        const val avatarUrlBase = "https://api.adorable.io/avatars/256/"
    }

    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        @field:SerializedName("geo")
        @field:Embedded(prefix = "geo_")
        val geo: Geo
    ) {
        data class Geo(
            @field:SerializedName("lat")
            val lat: String,
            @field:SerializedName("lng")
            val lng: String
        )
    }

    data class Company(
        @PrimaryKey val name: String,
        val catchPhrase: String,
        val bs: String
    )

    fun getAvatarUrl() = "$avatarUrlBase$email"
}