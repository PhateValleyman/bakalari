package cz.valleyman.bakalari.parent

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import cz.valleyman.bakalari.R
import cz.valleyman.bakalari.security.CommandSigner
import cz.valleyman.bakalari.security.SecurityManager
import cz.valleyman.bakalari.security.SignedPayload
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ParentActivity : AppCompatActivity() {
    
    private lateinit var securityManager: SecurityManager
    private val signer = CommandSigner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
        
        securityManager = SecurityManager(this)

        findViewById<Button>(R.id.btn_qr_unlock).setOnClickListener {
            showUnlockQR()
        }

        findViewById<Button>(R.id.btn_pair_device).setOnClickListener {
            showPairingQR()
        }
    }

    private fun showPairingQR() {
        val keyPair = securityManager.getOrCreateKeyPair()
        val pubKeyStr = securityManager.getPublicKeysString() ?: return
        
        val payload = mapOf(
            "type" to "PAIRING",
            "publicKey" to pubKeyStr
        )
        val jsonPayload = Json.encodeToString(payload)
        val bitmap = generateQR(jsonPayload)
        showQRDialog(bitmap)
    }

    private fun showUnlockQR() {
        val keyPair = securityManager.getOrCreateKeyPair()
        val timestamp = System.currentTimeMillis()
        val deviceId = "CHILD_DEVICE" // In real app, select from a list
        
        val dataToSign = "UNLOCK|$timestamp|$deviceId"
        val signature = signer.sign(dataToSign, keyPair.private)
        
        val payload = SignedPayload(
            command = "UNLOCK",
            timestamp = timestamp,
            deviceId = deviceId,
            signature = signature
        )
        
        val jsonPayload = Json.encodeToString(payload)
        val bitmap = generateQR(jsonPayload)
        
        showQRDialog(bitmap)
    }

    private fun generateQR(content: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    private fun showQRDialog(bitmap: Bitmap) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_qr, null)
        val ivQr = dialogView.findViewById<ImageView>(R.id.iv_qr)
        ivQr.setImageBitmap(bitmap)
        
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
            
        dialogView.findViewById<Button>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
}
