package eu.jsonplaceholder.ui.posts

import android.os.Bundle
import androidx.navigation.NavDirections
import eu.jsonplaceholder.R

class ShowDetailsDirection private constructor(){
    private data class ShowDetails(val name: String, val postId: Long): NavDirections{
        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putLong("postId", this.postId)
            result.putString("name", this.name)
            return result
        }

        override fun getActionId() = R.id.showDetails
    }
    companion object {
        fun showDetails(name: String, postId: Long): NavDirections = ShowDetails(name,postId)
    }
}