package com.ar.sharedtransition

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform