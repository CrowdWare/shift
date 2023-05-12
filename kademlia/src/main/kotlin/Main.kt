import com.google.gson.Gson
import kademlia.JKademliaNode
import kademlia.dht.GetParameter
import kademlia.node.KademliaId
import kademlia.node.Node
import kademlia.simulations.DHTContentImpl
import java.net.InetAddress

fun main(args: Array<String>) {
    println("SHIFT server")

    val server = JKademliaNode("server", KademliaId(), 12049);
    val c = DHTContentImpl(server.ownerId + "/msg", "This is a value from the server")
    println("before $c.key")
    server.put(c)

    println("after $c.key")
    /*
    val kad2 = JKademliaNode("OwnerName2", KademliaId(), 12057)

    val id = KademliaId()
    val ip = InetAddress.getLocalHost()// getByName("127.0.0.1")
    val node = Node(id, ip, 12049)
    kad2.bootstrap(node)

    val c = DHTContentImpl(kad2.ownerId + "/key", "ABCDEFG HIJKL")
    val c2 = DHTContentImpl(kad2.ownerId + "/key2", "Lorem ipsum dolor")
    kad2.put(c)
    kad2.put(c2)

    val gp = GetParameter(c.key, DHTContentImpl.TYPE)
    //gp.type = DHTContentImpl.TYPE
    gp.ownerId = c.ownerId
    val content = kad1.get(gp)

    val gson = Gson()
    val value = gson.fromJson(String(content.content), DHTContentImpl::class.java)

    println(value.data)
     */
}