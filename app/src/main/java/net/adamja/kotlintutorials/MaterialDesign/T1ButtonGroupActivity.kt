package net.adamja.kotlintutorials.MaterialDesign

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.adamja.kotlintutorials.R
import net.adamja.kotlintutorials.databinding.T1MdToggleButtonsBinding

class T1ButtonGroupActivity : AppCompatActivity() {
    // https://developer.android.com/topic/libraries/view-binding#kotlin
    private lateinit var binding: T1MdToggleButtonsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = T1MdToggleButtonsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->

            if (isChecked) {
                when (checkedId) {
                    R.id.btnAndroid -> showToast("Robot that looks like human.")
                    R.id.btnFlutter -> showToast("It's a Butterfly thing.")
                    R.id.btnWeb -> showToast("You still exist?")
                }
            } else {
                if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                    showToast("Nothing Selected")
                }
            }
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

}