package org.navfer.project.post

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String? = null,
    val userId: String,
    val image: String? = null,
    val info: String,
    val timestamp: Long = System.currentTimeMillis(),
    val comments: List<Comment>,
    val likes: List<Like>
)

@Serializable
data class Comment(
    val userId: String,
    val text: String?,
    val timestamp: Long = System.currentTimeMillis()
)
@Serializable
data class Like(
    val userId: String?
)
