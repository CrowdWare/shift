package kademlia;

import com.google.gson.Gson;
import kademlia.dht.KadContent;
import kademlia.node.KademliaId;
import kademlia.simulations.DHTContentImpl;

import java.io.Serializable;

public class KeyValueContent implements KadContent, Serializable {
    private static final long serialVersionUID = 1L;
    private final String TYPE = "KeyValueContentImpl";
    private long updateTs = 0L;
    private String ownerId = "";
    private long createTs = 0L;
    private KademliaId key;
    private String data = "";

    public KeyValueContent(String _key, String _value, String _owner) {
        createTs = System.currentTimeMillis() / 1000L;
        key = new KademliaId(_key);
        ownerId = _owner;
        data = _value;
    }

    public void setUpdated() {
        updateTs = System.currentTimeMillis() / 1000L;
    }

    public String getData() {
        return data;
    }

    @Override
    public KademliaId getKey() {
        return key;
    }

    @Override
    public String getType() {
        return DHTContentImpl.TYPE;
    }

    @Override
    public long getCreatedTimestamp() {
        return createTs;
    }

    @Override
    public long getLastUpdatedTimestamp() {
        return updateTs;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public byte[] toSerializedForm() {
        Gson gson = new Gson();
        return gson.toJson(this).getBytes();
    }

    @Override
    public KadContent fromSerializedForm(byte[] p) {
        Gson gson = new Gson();
        return gson.fromJson(new String(p), DHTContentImpl.class);
    }
}
