import kademlia.JKademliaNode;
import kademlia.node.KademliaId;
import kademlia.KeyValueContent;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server");
        try {
            JKademliaNode server = new JKademliaNode("server", new KademliaId(), 5000);
            KeyValueContent c = new KeyValueContent("12345678901234567890", "This is an awesome from the server", "server");        server.put(c);
        }
        catch(Exception e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
        }
    }
}