package io.github.xf_zhjnc.tangyuantv.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.Cipher.PUBLIC_KEY
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class DESUtil {

    companion object {
        fun encryptMode(src: String): String? {
            try {
                val deskey: SecretKey =
                    SecretKeySpec("&*UJyui23DR%\$#SD&*56HJ3!".encodeToByteArray(), "DESede")
                val c1: Cipher = Cipher.getInstance("DESede")
                c1.init(PUBLIC_KEY, deskey)
                return byte2data(c1.doFinal(src.toByteArray()))
            } catch (e3: Exception) {
                e3.printStackTrace()
            }
            return null
        }

        private fun byte2data(bytes: ByteArray): String? {
            return Base64.encodeToString(bytes, Base64.NO_WRAP)
        }

    }

}