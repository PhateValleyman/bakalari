package cz.valleyman.bakalari.security

import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.spec.X509EncodedKeySpec

class CommandSigner {
    
    companion object {
        private const val ALGORITHM = "SHA256withECDSA"
        private const val KEY_ALGO = "EC"
    }

    /**
     * Signs the data using the provided private key.
     */
    fun sign(data: String, privateKey: PrivateKey): String {
        val signature = Signature.getInstance(ALGORITHM)
        signature.initSign(privateKey)
        signature.update(data.toByteArray())
        return Base64.encodeToString(signature.sign(), Base64.NO_WRAP)
    }

    /**
     * Verifies the signature using the provided public key.
     */
    fun verify(data: String, signatureStr: String, publicKey: PublicKey): Boolean {
        return try {
            val signature = Signature.getInstance(ALGORITHM)
            signature.initVerify(publicKey)
            signature.update(data.toByteArray())
            val signatureBytes = Base64.decode(signatureStr, Base64.NO_WRAP)
            signature.verify(signatureBytes)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Generates a new EC key pair.
     */
    fun generateKeyPair(): java.security.KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGO)
        keyPairGenerator.initialize(256)
        return keyPairGenerator.generateKeyPair()
    }

    /**
     * Helper to reconstruct public key from string.
     */
    fun getPublicKeyFromString(keyStr: String): PublicKey {
        val keyBytes = Base64.decode(keyStr, Base64.NO_WRAP)
        val spec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGO)
        return keyFactory.generatePublic(spec)
    }
}
