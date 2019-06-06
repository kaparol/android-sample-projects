package eu.jsonplaceholder.util

import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.model.User

object TestUtil {
    const val postId = 1L
    const val title = "Some title"
    const val body = "Some body of this post"


    fun createPost(id: Long, userId: Long) = Post(
        id = id,
        title = title,
        body = body,
        userId = userId
    )


    fun createUser(id: Long, login: String) = User(
        id = id,
        name = login.replace(".", " "),
        username = login,
        company = createCompany("$login.company"),
        email = "$login.@mail.eu",
        phone = "+ 00 123 456 789",
        address = createAdress(),
        website = "$login.eu"
    )

    private fun createAdress() = User.Address(
        street = "street",
        city = "city",
        zipcode = "zipcode",
        suite = "sute",
        geo = User.Address.Geo("0.000", "0.000")
    )

    private fun createCompany(companyName: String) = User.Company(
        name = companyName,
        catchPhrase = "choose me",
        bs = "anything"
    )

}