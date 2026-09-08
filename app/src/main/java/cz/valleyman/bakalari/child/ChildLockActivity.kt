package cz.valleyman.bakalari.child

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cz.valleyman.bakalari.R

/**
 * Child device lock screen.
 */
class ChildLockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_lock)

        val reason = intent.getStringExtra("reason")
        if (reason != null) {
            findViewById<TextView>(R.id.tv_reason).text = reason
        }
    }

    override fun onBackPressed() {
        // Disable back button during lock
    }
}
