package org.navfer.project.user

import kotlinx.serialization.Serializable

@Serializable
data class UserSerializable(
    val id: String?,
    val username: String,
    val password: String,
    val avatar: String?
)