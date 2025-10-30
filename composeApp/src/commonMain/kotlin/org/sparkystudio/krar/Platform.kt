package org.sparkystudio.krar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform