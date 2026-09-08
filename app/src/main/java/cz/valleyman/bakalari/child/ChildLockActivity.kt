package cz.valleyman.bakalari.child

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.zxing.integration.android.IntentIntegrator
import cz.valleyman.bakalari.MainActivity
import cz.valleyman.bakalari.R
import cz.valleyman.bakalari.data.AppDatabase
import cz.valleyman.bakalari.security.CommandSigner
import cz.valleyman.bakalari.security.SecurityManager
import cz.valleyman.bakalari.security.SignedPayload
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Child device lock screen.
 */
class ChildLockActivity : AppCompatActivity() {
    
    private lateinit var securityManager: SecurityManager
    private val signer = CommandSigner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_lock)

        securityManager = SecurityManager(this)

        val reason = intent.getStringExtra("reason")
        if (reason != null) {
            findViewById<TextView>(R.id.tv_reason).text = reason
        }

        findViewById<Button>(R.id.btn_scan_qr).setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("Scan Parent QR Code")
            integrator.setBeepEnabled(true)
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                processScannedData(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun processScannedData(content: String) {
        try {
            val json = Json.parseToJsonElement(content).jsonObject
            
            if (json.containsKey("type") && json["type"]?.jsonPrimitive?.content == "PAIRING") {
                val pubKey = json["publicKey"]?.jsonPrimitive?.content
                if (pubKey != null) {
                    securityManager.saveRemotePublicKey(pubKey)
                    Toast.makeText(this, "Device Paired!", Toast.LENGTH_SHORT).show()
                }
            } else if (json.containsKey("command") && json["command"]?.jsonPrimitive?.content == "UNLOCK") {
                val payload = Json.decodeFromString<SignedPayload>(content)
                verifyAndUnlock(payload)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyAndUnlock(payload: SignedPayload) {
        val pubKey = securityManager.getRemotePublicKey()
        if (pubKey == null) {
            Toast.makeText(this, "Device not paired with parent!", Toast.LENGTH_LONG).show()
            return
        }

        val dataToVerify = "${payload.command}|${payload.timestamp}|${payload.deviceId}"
        val isValid = signer.verify(dataToVerify, payload.signature, pubKey)

        if (isValid) {
            unlockDevice()
        } else {
            Toast.makeText(this, "Invalid Signature!", Toast.LENGTH_LONG).show()
        }
    }

    private fun unlockDevice() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val allHomework = db.homeworkDao().getAll()
            val entities = allHomework.map { it.copy(completed = true) }
            db.homeworkDao().insertAll(entities)
            
            Toast.makeText(this@ChildLockActivity, "UNLOCKED!", Toast.LENGTH_SHORT).show()
            
            val intent = Intent(this@ChildLockActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        // Disable back button during lock
    }
}
