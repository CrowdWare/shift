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
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import at.crowdware.shift.MainActivity
import at.crowdware.shift.R
import at.crowdware.shift.logic.LocaleManager
import nl.tudelft.ipv8.android.service.IPv8Service


class ShiftChainService : IPv8Service() {
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
        return super.onStartCommand(intent, flags, startId)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.wrapContext(newBase!!))
    }
}
