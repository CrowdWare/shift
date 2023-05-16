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

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import at.crowdware.shift.service.ShiftChainService
import com.squareup.sqldelight.android.AndroidSqliteDriver
import nl.tudelft.ipv8.IPv8Configuration
import nl.tudelft.ipv8.Overlay
import nl.tudelft.ipv8.OverlayConfiguration
import nl.tudelft.ipv8.android.IPv8Android
import nl.tudelft.ipv8.android.keyvault.AndroidCryptoProvider
import nl.tudelft.ipv8.android.peerdiscovery.NetworkServiceDiscovery
import nl.tudelft.ipv8.attestation.trustchain.BlockListener
import nl.tudelft.ipv8.attestation.trustchain.BlockSigner
import nl.tudelft.ipv8.attestation.trustchain.TrustChainBlock
import nl.tudelft.ipv8.attestation.trustchain.TrustChainCommunity
import nl.tudelft.ipv8.attestation.trustchain.TrustChainSettings
import nl.tudelft.ipv8.attestation.trustchain.store.TrustChainSQLiteStore
import nl.tudelft.ipv8.attestation.trustchain.store.TrustChainStore
import nl.tudelft.ipv8.attestation.trustchain.validation.TransactionValidator
import nl.tudelft.ipv8.attestation.trustchain.validation.ValidationResult
import nl.tudelft.ipv8.keyvault.PrivateKey
import nl.tudelft.ipv8.peerdiscovery.DiscoveryCommunity
import nl.tudelft.ipv8.peerdiscovery.strategy.PeriodicSimilarity
import nl.tudelft.ipv8.peerdiscovery.strategy.RandomChurn
import nl.tudelft.ipv8.peerdiscovery.strategy.RandomWalk
import nl.tudelft.ipv8.sqldelight.Database
import nl.tudelft.ipv8.util.hexToBytes
import nl.tudelft.ipv8.util.toHex


object Network {

    fun initIPv8(application: Application) {
        val config = IPv8Configuration(overlays = listOf(
            createDiscoveryCommunity(application),
            createShiftCommunity()
        ), walkerInterval = 5.0)

        IPv8Android.Factory(application)
            .setConfiguration(config)
            .setPrivateKey(PersistanceManager.getPrivateKey(application))
            .setServiceClass(ShiftChainService::class.java)
            .init()

        initShiftCommunity(application)
    }

    private fun initShiftCommunity(application: Application) {
        val shift = IPv8Android.getInstance().getOverlay<ShiftCommunity>()!!
        shift.context = application
    }

    private fun createDiscoveryCommunity(application: Application): OverlayConfiguration<DiscoveryCommunity> {
        val randomWalk = RandomWalk.Factory()
        val randomChurn = RandomChurn.Factory()
        val periodicSimilarity = PeriodicSimilarity.Factory()
        val nsd = NetworkServiceDiscovery.Factory(application.getSystemService()!!)
        val strategies = mutableListOf(randomWalk, randomChurn, periodicSimilarity, nsd)
        return OverlayConfiguration(DiscoveryCommunity.Factory(), strategies)
    }

    private fun createShiftCommunity(): OverlayConfiguration<ShiftCommunity> {
        val randomWalk = RandomWalk.Factory()
        return OverlayConfiguration(
            Overlay.Factory(ShiftCommunity::class.java),
            listOf(randomWalk)
        )
    }
}