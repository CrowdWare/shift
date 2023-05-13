import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.gson.Gson

import kademlia.JKademliaNode
import kademlia.KeyValueContent
import kademlia.dht.GetParameter
import kademlia.node.KademliaId
import kademlia.node.Node
import kademlia.simulations.DHTContentImpl
import java.net.InetAddress

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    try {
        val client = JKademliaNode("client", KademliaId(), 4300)
        val id = KademliaId()
        val ip = InetAddress.getByName("128.140.48.116")
        val server = Node(id, ip, 5000)
        client.bootstrap(server)

        val searchKey = KademliaId("12345678901234567890".toByteArray())
        val gp = GetParameter(searchKey, DHTContentImpl.TYPE)
        gp.ownerId = "server"
        val content = client.get(gp)
        val gson = Gson()
        val value = gson.fromJson(String(content.content), KeyValueContent::class.java)

        println(value.getData())
    } catch (e: Exception) {
        println("An error occured: ${e.message}")
    }


    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
