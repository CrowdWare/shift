package at.crowdware.shift.ui;

import android.app.Application
import nl.tudelft.ipv8.android.keyvault.AndroidCryptoProvider
import nl.tudelft.ipv8.keyvault.defaultCryptoProvider

class ShiftApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        defaultCryptoProvider = AndroidCryptoProvider
    }
}
