package cz.valleyman.bakalari

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cz.valleyman.bakalari.child.ChildLockActivity
import cz.valleyman.bakalari.parent.ParentActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_parent_mode).setOnClickListener {
            startActivity(Intent(this, ParentActivity::class.java))
        }

        findViewById<Button>(R.id.btn_child_mode).setOnClickListener {
            startActivity(Intent(this, ChildLockActivity::class.java))
        }
    }
}
