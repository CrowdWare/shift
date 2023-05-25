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
import android.content.Intent
import android.util.Log
import androidx.core.content.getSystemService
import at.crowdware.shift.communities.ShiftCommunity
import at.crowdware.shift.service.ShiftChainService
import com.squareup.sqldelight.android.AndroidSqliteDriver
import nl.tudelft.ipv8.IPv8Configuration
import nl.tudelft.ipv8.Overlay
import nl.tudelft.ipv8.OverlayConfiguration
import nl.tudelft.ipv8.android.IPv8Android
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
import nl.tudelft.ipv8.peerdiscovery.DiscoveryCommunity
import nl.tudelft.ipv8.peerdiscovery.strategy.PeriodicSimilarity
import nl.tudelft.ipv8.peerdiscovery.strategy.RandomChurn
import nl.tudelft.ipv8.peerdiscovery.strategy.RandomWalk
import nl.tudelft.ipv8.sqldelight.Database
import java.time.LocalDate


object Network {

    var isStarted = false
        private set

    fun startServive(application: Application) {
        initIPv8(application)

        val serviceIntent = Intent(application, ShiftChainService::class.java)
        serviceIntent.putExtra("language", LocaleManager.getLanguage())
        application.startService(serviceIntent)
    }

    fun initIPv8(application: Application) {
        val config = IPv8Configuration(overlays = listOf(
            createDiscoveryCommunity(application),
            createTrustChainCommunity(application),
            createShiftCommunity()
        ), walkerInterval = 5.0)

        IPv8Android.Factory(application)
            .setConfiguration(config)
            .setPrivateKey(Backend.getPrivateKey())
            .setServiceClass(ShiftChainService::class.java)
            .init()

        initShiftCommunity(application)
        initTrustChain()
        isStarted = true
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

    private fun createTrustChainCommunity(application: Application): OverlayConfiguration<TrustChainCommunity> {
        val settings = TrustChainSettings()
        val driver = AndroidSqliteDriver(Database.Schema, application, "trustchain.db")
        val store = TrustChainSQLiteStore(Database(driver))
        val randomWalk = RandomWalk.Factory()

        val trustChainCommunity = OverlayConfiguration(
            TrustChainCommunity.Factory(settings, store),
            listOf(randomWalk)
        )
        return trustChainCommunity
    }

    private fun initTrustChain() {
        val ipv8 = IPv8Android.getInstance()
        val trustchain = ipv8.getOverlay<TrustChainCommunity>()!!

        trustchain.registerTransactionValidator(Backend.BLOCK_TYPE, object : TransactionValidator {
            override fun validate(
                block: TrustChainBlock,
                database: TrustChainStore
            ): ValidationResult {
                if (block.isAgreement)
                    return ValidationResult.Valid
                val (amount, type, date) = Backend.parseTransaction(block)
                return if ((type == TransactionType.INITIAL_BOOKING && date == LocalDate.now())
                    || (type == TransactionType.SCOOPED && amount * 1000UL <= Backend.getMaxGrow().toULong()
                            && date == LocalDate.now().minusDays(1))) {
                    Log.d("TrustChainDemo", "Validating block true")
                    ValidationResult.Valid
                } else {
                    Log.d("TrustChainDemo", "Validating block false: $amount, $type, $date")
                    ValidationResult.Invalid(listOf(""))
                }
            }
        })

        trustchain.registerBlockSigner(Backend.BLOCK_TYPE, object : BlockSigner {
            override fun onSignatureRequest(block: TrustChainBlock) {
                Log.d("TrustChainDemo", "creating agreement")
                trustchain.createAgreementBlock(block, mapOf<Any?, Any?>())
            }
        })

        trustchain.addListener(Backend.BLOCK_TYPE, object : BlockListener {
            override fun onBlockReceived(block: TrustChainBlock) {
                Log.d("TrustChainDemo", "onBlockReceived ${block.transaction}")
            }
        })
    }
}