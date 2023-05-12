import kademlia.JKademliaNode
import kademlia.node.KademliaId
import kademlia.KeyValueContent


fun main(args: Array<String>) {
    println("Server")

    val server = JKademliaNode("server", KademliaId(), 12049);
    val c = KeyValueContent("12345678901234567890", "This is an awesome from the server", "server")
    server.put(c)
}