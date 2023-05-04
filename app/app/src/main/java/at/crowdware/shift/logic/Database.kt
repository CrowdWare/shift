package at.crowdware.shift.logic
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.content.Context
import android.os.Environment
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import at.crowdware.shift.MainActivity
import at.crowdware.shift.R
import at.crowdware.shift.RegistrationActivity
import com.loopj.android.http.AsyncHttpClient
import cz.msebera.android.httpclient.Header
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.entity.ByteArrayEntity
import cz.msebera.android.httpclient.message.BasicHeader
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.Date
import java.util.UUID

class Database {
    companion object {
        private const val keyPhrase = "1234567812345678" // TODO...RAUS DAMIT
        private const val db_name = "shift.db"

        fun getDbFile(context: AppCompatActivity): File
        {
            var file: File

            file = File(context.applicationContext.filesDir, db_name)
            if (!file.exists())
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                    file = File(context.getExternalFilesDir(null), db_name)
            return file
        }

        fun saveAccount(context: AppCompatActivity, account: Account) {
            val file = getDbFile(context)

            // Create a key for the encryption
            val key = SecretKeySpec(keyPhrase.toByteArray(), "AES")

            // Serialize the person object to a byte array
            val baos = ByteArrayOutputStream()
            ObjectOutputStream(baos).use { it.writeObject(account) }
            val serializedData = baos.toByteArray()

            // Encrypt the serialized data
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encryptedData = cipher.doFinal(serializedData)

            // Write the encrypted data to a file
            file.writeBytes(encryptedData)
        }

        fun readAccount(context: MainActivity): Account {
            var account: Account
            // Read the encrypted data from the file
            val file = getDbFile(context)
            val encryptedDataFromFile = file.readBytes()

            val key = SecretKeySpec(keyPhrase.toByteArray(), "AES")

            // Decrypt the encrypted data
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decryptedData = cipher.doFinal(encryptedDataFromFile)

            // Deserialize the decrypted data into a person object
            ObjectInputStream(ByteArrayInputStream(decryptedData)).use {
                account = it.readObject() as Account
            }
            return account
        }
    }
}