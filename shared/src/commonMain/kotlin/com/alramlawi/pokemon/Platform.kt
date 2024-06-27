package com.alramlawi.pokemon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform