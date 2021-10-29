package net.adamja.testlivedata.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

class MainViewModel : ViewModel() {

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

    companion object {
        private const val TAG = "MainViewModel"
    }
}

data class Test(
    var count: Int = 0
)