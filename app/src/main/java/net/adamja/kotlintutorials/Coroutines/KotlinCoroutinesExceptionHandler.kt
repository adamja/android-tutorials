package net.adamja.kotlintutorials.Coroutines

import kotlinx.coroutines.CoroutineExceptionHandler

class KotlinCoroutinesExceptionHandler {

    // https://kotlinlang.org/docs/exception-handling.html#supervision-scope
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
}