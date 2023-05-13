package at.crowdware.shift

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import at.crowdware.shift.ui.theme.DrawerComposeTheme
import at.crowdware.shift.logic.Database
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.ShiftCommunity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KLogger
import nl.tudelft.ipv8.*
import nl.tudelft.ipv8.keyvault.JavaCryptoProvider
import nl.tudelft.ipv8.messaging.EndpointAggregator
import nl.tudelft.ipv8.messaging.udp.UdpEndpoint
import java.net.InetAddress
import java.util.Date
import kotlin.math.roundToInt
import mu.KotlinLogging
import nl.tudelft.ipv8.peerdiscovery.strategy.RandomWalk
import com.goterl.lazysodium.LazySodiumAndroid
import com.goterl.lazysodium.LazySodium

class MainActivity : ComponentActivity() {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val logger = KotlinLogging.logger {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrawerComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LocaleManager.init(applicationContext)
                    startIpv8(scope, logger)
                    val hasJoined = remember { mutableStateOf(hasJoined(applicationContext)) }
                    if (hasJoined.value)
                        NavigationView()
                    else
                        JoinForm(hasJoined)
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.wrapContext(newBase!!))
    }
}

fun hasJoined(applicationContext: Context): Boolean
{
   return Database.readAccount(applicationContext) != null
}

fun startIpv8(scope: CoroutineScope, logger: KLogger) {

    val myKey = JavaCryptoProvider.generateKey()
    val myPeer = Peer(myKey)
    val udpEndpoint = UdpEndpoint(8090, InetAddress.getByName("0.0.0.0"))
    val endpoint = EndpointAggregator(udpEndpoint, null)

    val config = IPv8Configuration(overlays = listOf(
        //createDiscoveryCommunity(),
        //createTrustChainCommunity(),
        createShiftCommunity()
    ), walkerInterval = 1.0)

    val ipv8 = IPv8(endpoint, config, myPeer)
    ipv8.start()

    scope.launch {
        while (true) {
            for ((_, overlay) in ipv8.overlays) {
                printPeersInfo(overlay, logger)
            }
            logger.info("===")
            delay(5000)
        }
    }

    //while (ipv8.isStarted()) {
    //    Thread.sleep(1000)
    //}
}

fun printPeersInfo(overlay: Overlay, logger: KLogger) {
    val peers = overlay.getPeers()
    logger.info(overlay::class.simpleName + ": ${peers.size} peers")
    for (peer in peers) {
        val avgPing = peer.getAveragePing()
        val lastRequest = peer.lastRequest
        val lastResponse = peer.lastResponse

        val lastRequestStr = if (lastRequest != null)
            "" + ((Date().time - lastRequest.time) / 1000.0).roundToInt() + " s" else "?"

        val lastResponseStr = if (lastResponse != null)
            "" + ((Date().time - lastResponse.time) / 1000.0).roundToInt() + " s" else "?"

        val avgPingStr = if (!avgPing.isNaN()) "" + (avgPing * 1000).roundToInt() + " ms" else "? ms"
        logger.info("${peer.mid} (S: ${lastRequestStr}, R: ${lastResponseStr}, ${avgPingStr})")
    }
}

private fun createShiftCommunity(): OverlayConfiguration<ShiftCommunity> {
    val randomWalk = RandomWalk.Factory(timeout = 3.0, peers = 20)
    return OverlayConfiguration(
        Overlay.Factory(ShiftCommunity::class.java),
        listOf(randomWalk)
    )
}