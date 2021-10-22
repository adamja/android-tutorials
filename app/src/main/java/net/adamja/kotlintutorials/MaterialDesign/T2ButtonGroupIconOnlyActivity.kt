package net.adamja.kotlintutorials.MaterialDesign

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.adamja.kotlintutorials.R
import net.adamja.kotlintutorials.databinding.T2MdToggleButtonsIconOnlyBinding


/**
 * Tutorial: Smartherd YouTube Series
 * Name: How to create Toggle buttons with icon Only. Android Studio Tutorial (Kotlin)
 * Source: https://www.youtube.com/watch?v=jjFxl04HEFM&list=PLlxmoA0rQ-Ly0OKjw-_5jivHSRgaGVBDW&index=3
 *
 */
class T2ButtonGroupIconOnlyActivity : AppCompatActivity() {
    // https://developer.android.com/topic/libraries/view-binding#kotlin
    private lateinit var binding: T2MdToggleButtonsIconOnlyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = T2MdToggleButtonsIconOnlyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnLeft -> showToast("Left Align")
                    R.id.btnCenter -> showToast("Center Align")
                    R.id.btnRight -> showToast("Right Align")
                    R.id.btnJustified -> showToast("Justified Align")
                }
            } else {
                if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                    showToast("No Alignment Selected")
                }
            }
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

}