/****************************************************************************
 * Copyright (C) 2023 CrowdWare
 *
 * This file is part of SHIFT.
 *
 *  SHIFT is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SHIFT is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************/
package at.crowdware.shift.logic
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.content.Context
import android.os.Environment
import androidx.activity.compose.BackHandler
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class Database {
    companion object {
        private const val keyPhrase = "1234567812345678"  // must be 16 bytes long   // TODO...RAUS DAMIT
        private const val db_name = "shift.db"

        fun saveAccount(context: Context) {
            val file = File(context.applicationContext.filesDir, db_name)
            try {
                // Create a key for the encryption
                val key = SecretKeySpec(keyPhrase.toByteArray(), "AES")

                // Serialize the person object to a byte array
                val baos = ByteArrayOutputStream()
                ObjectOutputStream(baos).use {
                    it.writeObject(Backend.getAccount())
                }
                val serializedData = baos.toByteArray()

                // Encrypt the serialized data
                val cipher = Cipher.getInstance("AES")
                cipher.init(Cipher.ENCRYPT_MODE, key)
                val encryptedData = cipher.doFinal(serializedData)

                // Write the encrypted data to a file
                file.writeBytes(encryptedData)
            }
            catch(e: java.lang.Exception)
            {
                if(file.exists())
                    file.delete()
            }
        }

        fun readAccount(context: Context): Account? {
            val file = File(context.applicationContext.filesDir, db_name)
            if(!file.exists()) {
                return null
            }
            try {
                var account: Account

                // Read the encrypted data from the file
                val encryptedDataFromFile = file.readBytes()
                val key = SecretKeySpec(keyPhrase.toByteArray(), "AES")
                val cipher = Cipher.getInstance("AES")
                cipher.init(Cipher.DECRYPT_MODE, key)
                val decryptedData = cipher.doFinal(encryptedDataFromFile)
                ObjectInputStream(ByteArrayInputStream(decryptedData)).use {
                    account = it.readObject() as Account
                }
                Backend.setAccount(account)
                return account
            }
            catch (e: Exception) {
                println("An error occurred reading the database: " + e.message)
                return null
            }
        }
    }
}