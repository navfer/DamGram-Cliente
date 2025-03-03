package org.navfer.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform