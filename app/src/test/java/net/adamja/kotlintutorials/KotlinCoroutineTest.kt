package net.adamja.kotlintutorials

import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import net.adamja.kotlintutorials.Coroutines.myOwnSuspendingFunc
import org.junit.Test

class KotlinCoroutineTest {

    @Test
    fun myFirstTest() = runBlocking {
        myOwnSuspendingFunc()
        Assert.assertEquals(10, 5 + 5)

    }
}