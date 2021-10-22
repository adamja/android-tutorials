package net.adamja.kotlintutorials.Coroutines

import kotlinx.coroutines.*


/**
 * Tutorial - YouTube
 * Name: Kotlin Coroutine Cancellation: Cooperative cancellation, Handle Exceptions, and Timeouts
 * Source: https://www.youtube.com/watch?v=CswqyZJikxI&t=0s
 *
 * --- Notes:
 * Make coroutine cooperative with delay(), yield(), withContext(), withTimeout()
 *
 */


fun main() {
    println("\nMain program starts: ${Thread.currentThread().name}")

    KotlinCoroutines3.test13()

    println("\nMain program finished: ${Thread.currentThread().name}")
}

class KotlinCoroutines3 {

    // Static methods
    companion object {

        /**
         * Non-cooperative coroutine - because it uses Thread.sleep which is not a coroutine method
         */
        fun test1() = runBlocking {
            val job: Job = launch {
                for (i in 0..100) {
                    print("$i.")
                    Thread.sleep(50)
                }
            }

            delay(200)
            job.cancel()
            job.join()
        }

        /**
         * delay() which is a coroutine method
         */
        fun test2() = runBlocking {
            val job: Job = launch {
                for (i in 0..100) {
                    print("$i.")
                    delay(50)
                }
            }

            delay(200)
//            job.cancel()
//            job.join()
            job.cancelAndJoin()
        }

        /**
         * yield() function
         */
        fun test3() = runBlocking {
            val job: Job = launch {
                for (i in 0..100) {
                    print("$i.")
                    yield()  // no delay but still cancelable
                }
            }

            delay(4)
//            job.cancel()
//            job.join()
            job.cancelAndJoin()
        }

        /**
         * isActive flag to make the coroutine cooperative
         */
        fun test4() = runBlocking {
            val job: Job = launch(Dispatchers.Default) {
                for (i in 0..100) {

                    if (!isActive) {  // coroutine flag
                        break;
                    }

                    print("$i.")
                    Thread.sleep(1)
                }
            }

            delay(4)
            job.cancelAndJoin()  // sets isActive to false
        }

        /**
         * return@launch - stop executing more code and return to where the coroutine was called
         */
        fun test5() = runBlocking {
            val job: Job = launch(Dispatchers.Default) {
                for (i in 0..100) {

                    if (!isActive) {  // coroutine flag
                        return@launch
                    }

                    print("$i.")
                    Thread.sleep(1)
                }
            }

            delay(4)
            job.cancelAndJoin()  // sets isActive to false
        }

        /**
         * try/catch/finally exception handling
         */
        fun test6() = runBlocking {
            val job: Job = launch(Dispatchers.Default) {

                try {
                    for (i in 0..500) {

                        print("$i.")
                        delay(5)
                    }
                } catch (e: CancellationException) {
                    print("\nException caught safely")
                } finally {
                    print("\nCloses resources in finally")
                }

            }

            delay(10)
            job.cancelAndJoin()  // sets isActive to false
        }

        /**
         * try/catch/finally with delay in finally
         */
        fun test7() = runBlocking {
            val job: Job = launch(Dispatchers.Default) {

                try {
                    for (i in 0..500) {

                        print("$i.")
                        delay(5)
                    }
                } catch (e: CancellationException) {
                    print("\nException caught safely")
                } finally {
                    delay(1000)  // generally we don't use suspending function in finally
                    print("\nCloses resources in finally")
                }

            }

            delay(10)
            job.cancelAndJoin()  // sets isActive to false
        }

        /**
         * withContext(NonCancellable)
         * make sure code is executed before cancelling
         */
        fun test8() = runBlocking {
            val job: Job = launch(Dispatchers.Default) {

                try {
                    for (i in 0..500) {

                        print("$i.")
                        delay(5)
                    }
                } catch (e: CancellationException) {
                    print("\nException caught safely")
                } finally {
                    // is a coroutine builder, creates another coroutine in this context
                    withContext(NonCancellable) {
                        delay(1000)  // generally we don't use suspending function in finally
                        print("\nCloses resources in finally")
                    }

                }

            }

            delay(10)
            job.cancelAndJoin()  // sets isActive to false
        }

        /**
         * CancellationException - create your own on job.cancel()
         */
        fun test9() = runBlocking {
            val job: Job = launch(Dispatchers.Default) {

                try {
                    for (i in 0..500) {

                        print("$i.")
                        delay(5)
                    }
                } catch (e: CancellationException) {
                    print("\nException caught safely: ${e.message}")
                } finally {
                    // is a coroutine builder, creates another coroutine in this context
                    withContext(NonCancellable) {
                        delay(1000)  // generally we don't use suspending function in finally
                        print("\nCloses resources in finally")
                    }

                }

            }

            delay(30)
            job.cancel(CancellationException("My own crash message"))
            job.join()
        }

        /**
         * Timeouts - withTimeout()
         */
        fun test10() = runBlocking {

            withTimeout(2000) {
                for (i in 0..500) {

                    print("$i.")
                    delay(500)
                }
            }
        }


        /**
         * Timeouts - withTimeout() with try/catch
         */
        fun test11() = runBlocking {

            try {

                withTimeout(2000) {

                    for (i in 0..500) {

                        print("$i.")
                        delay(500)
                    }
                }

            } catch (ex: TimeoutCancellationException) {
                print("\nCatch: ${ex.message}")
            } finally {
                print("\nFinally")
                // ... code
            }

        }


        /**
         * Timeouts - withTimeoutOrNull()
         * no exception thrown - no need for try catch
         */
        fun test12() = runBlocking {

            withTimeoutOrNull(2000) {

                for (i in 0..500) {

                    print("$i.")
                    delay(500)
                }
            }
        }


        /**
         * Timeouts - withTimeoutOrNull()
         * get value from withTimeoutOrNull() function
         * return null if there is a timeout
         * return String if finished successfully
         */
        fun test13() = runBlocking {

            val res: String? = withTimeoutOrNull(2000) {

                for (i in 0..500) {

                    print("$i.")
                    delay(500)
                }
                "I am done..."
            }

            println("String result: $res")
        }


    }
}