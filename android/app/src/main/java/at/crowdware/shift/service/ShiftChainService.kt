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
package at.crowdware.shift.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import at.crowdware.shift.MainActivity
import at.crowdware.shift.R
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.logic.LocaleManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.tudelft.ipv8.android.service.IPv8Service


class ShiftChainService : IPv8Service() {
    private val INTERVAL: Long = 1000 * 60 // 1 minute
    private var isServiceStarted = false
    private var wakeLock: PowerManager.WakeLock? = null
    private var startTime: ULong = 0UL
    private val SHIFT = "shift"
    private val serviceScope = CoroutineScope(Dispatchers.Main)

    override fun createNotification(): NotificationCompat.Builder {
        val trustChainExplorerIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(trustChainExplorerIntent)
            .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_CONNECTION)
            .setContentTitle("Shift-Service")
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.service_description)))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val language = intent?.getStringExtra("language")
        if (!language.isNullOrEmpty()) {
            LocaleManager.setLocale(applicationContext, language)
        }
        startServiceLoop()
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    private fun startServiceLoop() {
        if (isServiceStarted)
            return
        isServiceStarted = true
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ShiftChainService::lock").apply {
                    acquire()
                }
            }

        serviceScope.launch {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    scoopLiquid()
                }
                delay(INTERVAL)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false
        serviceScope.cancel()
    }

    override fun onCreate() {
        super.onCreate()
        startTime =  (System.currentTimeMillis() / 1000).toULong()
    }

    private fun scoopLiquid() {
        val context = this;
        val time = (System.currentTimeMillis() / 1000).toULong()
        val account = Backend.getAccount()
        if (account.scooping > 0UL) {
            val scoopingSeconds = time - account.scooping
            val serviceSeconds = time - startTime
            val hoursScooped = kotlin.math.min(
                scoopingSeconds.toFloat() / 3600,
                serviceSeconds.toFloat() / 3600
            )
            if (hoursScooped >= 1f) {
                Backend.addLiquid(context, hoursScooped)
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.wrapContext(newBase!!))
    }
}
