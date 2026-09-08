package cz.valleyman.bakalari

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cz.valleyman.bakalari.child.ChildLockActivity
import cz.valleyman.bakalari.data.AppDatabase
import cz.valleyman.bakalari.parent.ParentActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Basic startup verification: check for pending homework
        checkLockStateAndRedirect()
        
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_parent_mode).setOnClickListener {
            startActivity(Intent(this, ParentActivity::class.java))
        }

        findViewById<Button>(R.id.btn_child_mode).setOnClickListener {
            startActivity(Intent(this, ChildLockActivity::class.java))
        }
    }

    private fun checkLockStateAndRedirect() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val allHomework = db.homeworkDao().getAll()
            val pendingHomework = allHomework.filter { !it.completed }
            
            if (pendingHomework.isNotEmpty()) {
                // If there is pending homework, force ChildLockActivity
                val intent = Intent(this@MainActivity, ChildLockActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra("reason", "Máš ${pendingHomework.size} nesplněných úkolů.")
                }
                startActivity(intent)
                finish()
            }
        }
    }
}
