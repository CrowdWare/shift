package kademlia

import java.nio.charset.StandardCharsets
import com.google.gson.Gson
import kademlia.dht.KadContent
import kademlia.node.KademliaId
import kademlia.simulations.DHTContentImpl


class KeyValueContent(_key: String, _value: String, _owner: String): KadContent {
    @Transient
    val TYPE = "KeyValueContentImpl"

    private var updateTs = 0L
    private var ownerId = ""
    private var createTs = 0L
    private var key: KademliaId
    private var data: String = ""

    init{
        createTs = System.currentTimeMillis() / 1000L
        key = KademliaId(_key)
        ownerId = _owner
        data = _value
    }

    fun setUpdated() {
        updateTs = System.currentTimeMillis() / 1000L
    }

    fun getData():String {
        return data
    }
    override fun getKey(): KademliaId {
        return key
    }

    override fun getType(): String {
        return DHTContentImpl.TYPE
    }

    override fun getCreatedTimestamp(): Long {
        return createTs
    }

    override fun getLastUpdatedTimestamp(): Long {
        return updateTs
    }

    override fun getOwnerId(): String {
        return ownerId
    }

    override fun toSerializedForm(): ByteArray {
        val gson = Gson()
        return gson.toJson(this).toByteArray()

    }

    override fun fromSerializedForm(p: ByteArray?): KadContent {
        val gson = Gson()
        return gson.fromJson(String(p!!), DHTContentImpl::class.java)
    }
}