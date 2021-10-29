package net.adamja.livedatatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.adamja.livedatatest.ui.main.MainFragment
import net.adamja.livedatatest.ui.main.SecondFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .replace(R.id.container2, SecondFragment.newInstance())
                .commitNow()
        }
    }
}