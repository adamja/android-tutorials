package net.adamja.livedatatest.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {

    // --- test1
    private var test: Test = Test()


    private val testLD: LiveData<Test> = liveData {
        emit(test)

        while (true) {
            delay(2000)
            test.count++
            emit(test)
        }

    }

    fun getTest(): LiveData<Test> {
        return testLD
    }


    // --- test 2
    private var test2: Test = Test()

    private val test2LD: LiveData<Test> = liveData {
        emit(test2)
    }

    suspend fun incrementCountWithDelay(): Test {
        delay(2000)
        incrementCount()
        return test
    }

    fun getTest2(): LiveData<Test> {
        return test2LD
    }

    fun incrementCount() {
        test2.count++
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}

data class Test(
    var count: Int = 0
)