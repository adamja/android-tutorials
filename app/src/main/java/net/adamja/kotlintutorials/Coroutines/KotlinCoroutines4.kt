package net.adamja.kotlintutorials.Coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Tutorial - YouTube
 * Name: Kotlin Coroutines: Composing Suspending Functions - Sequential, Concurrent, and Lazy Execution
 * Source: https://www.youtube.com/watch?v=aGR11WCd8rg&t=0s
 *
 * --- Notes:
 *
 */

fun main() {
    println("\nMain program starts: ${Thread.currentThread().name}")

    KotlinCoroutines4.test4()

    println("\nMain program finished: ${Thread.currentThread().name}")
}


class KotlinCoroutines4 {

    // Static methods
    companion object {

        /**
         * Sequential coroutine - runBlocking()
         */
        fun test1() = runBlocking {

            val time = measureTimeMillis {
                val msgOne = getMessageOne()
                val msgTwo = getMessageTwo()
                println("The entire message is: ${msgOne + msgTwo}")
            }


            println("Completed in: $time ms")

        }

        suspend fun getMessageOne(): String {
            delay(1000L)  // pretend to do some work
            println("After working in getMessageOne()")
            return "Hello "
        }

        suspend fun getMessageTwo(): String {
            delay(1000L)  // pretend to do some work
            println("After working in getMessageTwo()")
            return "World!"
        }

        /**
         * Concurrent coroutine - async/await
         *
         * async creates a coroutine within the parent coroutine which means they can run in parallel
         *
         * launch() also will run in parallel, but you cannot return a value
         */
        fun test2() = runBlocking {

            val time = measureTimeMillis {
                val msgOne: Deferred<String> = async { getMessageOne() }
                val msgTwo: Deferred<String> = async { getMessageTwo() }

                println("The entire message is: ${msgOne.await() + msgTwo.await()}")
            }


            println("Completed in: $time ms")

        }

        /**
         * lazy - run if values are required
         */
        fun test3() = runBlocking {

            val msgOne: Deferred<String> = async(start = CoroutineStart.LAZY) { getMessageOne() }
            val msgTwo: Deferred<String> = async(start = CoroutineStart.LAZY) { getMessageTwo() }

            println("The entire message is: ${msgOne.await() + msgTwo.await()}")

        }

        /**
         * lazy - don't waste resouces running a function if the result is not used
         */
        fun test4() = runBlocking {

            val msgOne: Deferred<String> = async(start = CoroutineStart.LAZY) { getMessageOne() }
            val msgTwo: Deferred<String> = async(start = CoroutineStart.LAZY) { getMessageTwo() }

//            println("The entire message is: ${msgOne.await() + msgTwo.await()}")

        }

    }


}