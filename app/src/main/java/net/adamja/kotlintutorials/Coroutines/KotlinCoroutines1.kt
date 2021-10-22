package net.adamja.kotlintutorials.Coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


/**
 * Tutorial - YouTube
 * Source: https://www.youtube.com/watch?v=C38lG2wraoo
 *
 * --- Notes:
 * Thread.sleep() blocks the thread
 *
 * delay() does not block the thread - can only be called within a coroutine
 *
 * GlobalScope.launch() is non-blocking in nature, whereas
 * runBlocking() blocks the thread in which it operates
 *
 */


fun main() {
    println("Main program starts: ${Thread.currentThread().name}")

    test6()

    println("Main program finished: ${Thread.currentThread().name}")
}

fun test1StandardThread() {

    // Standard Thread
    thread {
        println("Fake thread work starts: ${Thread.currentThread().name}")
        Thread.sleep(1000);
        println("Fake thread work finished: ${Thread.currentThread().name}")
    }
}

fun test2CoroutineSleep() {
    // Coroutine: Global Scope
    // Note: Coroutine will not run once the net.adamja.kotlintutorials.Coroutines.main thread is killed
    GlobalScope.launch {
        println("Fake GlobalScope work starts: ${Thread.currentThread().name}")
        delay(1000);
        println("Fake GlobalScope work finished: ${Thread.currentThread().name}")
    }

    // Blocks the current net.adamja.kotlintutorials.Coroutines.main thread & wait for coroutine to finish
    Thread.sleep(2000);
}

fun test3CoroutineDelay() {

    // Coroutine: Global Scope
    // Note: Coroutine will not run once the net.adamja.kotlintutorials.Coroutines.main thread is killed
    GlobalScope.launch {
        println("Fake GlobalScope work starts: ${Thread.currentThread().name}")
        delay(1000);
        println("Fake GlobalScope work finished: ${Thread.currentThread().name}")
    }

    runBlocking { // Creates a coroutine that blocks the current net.adamja.kotlintutorials.Coroutines.main thread
        delay(2000);  // wait for coroutine to finish (practically not a right way to wait)
    }
}

fun test4AllWrappedInCoroutine() {

    runBlocking {
        println("Main (4) program starts: ${Thread.currentThread().name}")

        GlobalScope.launch {
            println("Fake GlobalScope work starts: ${Thread.currentThread().name}")
            delay(1000);
            println("Fake GlobalScope work finished: ${Thread.currentThread().name}")
        }

        delay(2000);

        println("Main (4) program finished: ${Thread.currentThread().name}")
    }

}

fun test5() = runBlocking {

        println("Main (5) program starts: ${Thread.currentThread().name}")

        GlobalScope.launch {
            println("Fake GlobalScope work starts: ${Thread.currentThread().name}")
            delay(1000);
            println("Fake GlobalScope work finished: ${Thread.currentThread().name}")
        }

        delay(2000);

        println("Main (5) program finished: ${Thread.currentThread().name}")

}

fun test6() {
    runBlocking {
        println("net.adamja.kotlintutorials.Coroutines.test6 starts: ${Thread.currentThread().name}")
        test6SuspendFunc(1000)
        println("net.adamja.kotlintutorials.Coroutines.test6 finished: ${Thread.currentThread().name}")
    }
}

suspend fun test6SuspendFunc(time: Long) {
    // code...
    delay(time);
}