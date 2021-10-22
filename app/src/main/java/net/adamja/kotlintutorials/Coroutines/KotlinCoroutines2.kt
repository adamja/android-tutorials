package net.adamja.kotlintutorials.Coroutines

import kotlinx.coroutines.*

/**
 * Tutorial - YouTube
 * Name: Kotlin Coroutine Builders: launch, async, and runBlocking along with GlobalScope companion object
 * Source: https://www.youtube.com/watch?v=a56bmbOvfHY&t=0s
 *
 * --- Notes:
 *
 *
 */


fun main() {
    println("Main program starts: ${Thread.currentThread().name}")

    test4()

    println("Main program finished: ${Thread.currentThread().name}")
}


// launch() example
fun test1() {
    // Creates coroutine at global (app) level
    GlobalScope.launch {
        delay(1000)
    }


    // Creates coroutine at local scope level
    runBlocking {
        println("launch program starts: ${Thread.currentThread().name}")

        val job = launch {
            println("Coroutine program starts: ${Thread.currentThread().name}")
            delay(1000)
            println("Coroutine program finished: ${Thread.currentThread().name}")
        }

        // job.cancel() -> cancel the job
        job.join()
        println("launch program finished: ${Thread.currentThread().name}")
    }
}


/**
 * async() example - no return value
 */
fun test2() = runBlocking {

        println("launch program starts: ${Thread.currentThread().name}")

        val jobDeferred: Deferred<Unit> = async {
            println("Coroutine program starts: ${Thread.currentThread().name}")
            delay(1000)
            println("Coroutine program finished: ${Thread.currentThread().name}")
        }

        // job.cancel() -> cancel the job
        jobDeferred.join()
        println("launch program finished: ${Thread.currentThread().name}")

}

/**
 * async() example - return value
 */
fun test3() = runBlocking {

    println("launch program starts: ${Thread.currentThread().name}")

    val jobDeferred: Deferred<String> = async {
        println("Coroutine program starts: ${Thread.currentThread().name}")
        delay(1000)
        println("Coroutine program finished: ${Thread.currentThread().name}")
        "Return String Value"
    }

    // job.cancel() -> cancel the job
    val res: String = jobDeferred.await()
    println("Coroutine result: \"$res\"")
    println("launch program finished: ${Thread.currentThread().name}")

}

/**
 * async() example - GlobalScope with return value
 */
fun test4() = runBlocking {

    println("launch program starts: ${Thread.currentThread().name}")

    val jobDeferred: Deferred<String> = GlobalScope.async {
        println("Coroutine program starts: ${Thread.currentThread().name}")
        delay(1000)
        println("Coroutine program finished: ${Thread.currentThread().name}")
        "Return String Value"
    }

    // job.cancel() -> cancel the job
    val res: String = jobDeferred.await()
    println("Coroutine result: \"$res\"")
    println("launch program finished: ${Thread.currentThread().name}")

}

/**
 * this is used in a test function in the test folder
 */
suspend fun myOwnSuspendingFunc() {
    delay(1000)
}

