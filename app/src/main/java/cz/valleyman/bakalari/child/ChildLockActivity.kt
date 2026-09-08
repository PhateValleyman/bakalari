package cz.valleyman.bakalari.child

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.valleyman.bakalari.R

/**
 * Child device lock screen.
 */
class ChildLockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_lock)
    }

    override fun onBackPressed() {
        // Disable back button during lock
    }
}
