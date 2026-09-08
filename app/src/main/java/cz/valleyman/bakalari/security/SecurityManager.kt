package cz.valleyman.bakalari.security

import android.content.Context
import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

class SecurityManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("security_prefs", Context.MODE_PRIVATE)
    private val signer = CommandSigner()

    fun getOrCreateKeyPair(): KeyPair {
        val privStr = prefs.getString("private_key", null)
        val pubStr = prefs.getString("public_key", null)

        return if (privStr != null && pubStr != null) {
            val keyFactory = KeyFactory.getInstance("EC")
            
            val privBytes = Base64.decode(privStr, Base64.NO_WRAP)
            val privSpec = PKCS8EncodedKeySpec(privBytes)
            val privateKey = keyFactory.generatePrivate(privSpec)

            val pubBytes = Base64.decode(pubStr, Base64.NO_WRAP)
            val pubSpec = X509EncodedKeySpec(pubBytes)
            val publicKey = keyFactory.generatePublic(pubSpec)

            KeyPair(publicKey, privateKey)
        } else {
            val kp = signer.generateKeyPair()
            prefs.edit().apply {
                putString("private_key", Base64.encodeToString(kp.private.encoded, Base64.NO_WRAP))
                putString("public_key", Base64.encodeToString(kp.public.encoded, Base64.NO_WRAP))
                apply()
            }
            kp
        }
    }

    fun getPublicKeysString(): String? {
        return prefs.getString("public_key", null)
    }

    fun saveRemotePublicKey(keyStr: String) {
        prefs.edit().putString("remote_public_key", keyStr).apply()
    }

    fun getRemotePublicKey(): PublicKey? {
        val keyStr = prefs.getString("remote_public_key", null) ?: return null
        return signer.getPublicKeyFromString(keyStr)
    }
}
