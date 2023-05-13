package nl.tudelft.ipv8.attestation.trustchain.store

import nl.tudelft.ipv8.attestation.trustchain.TrustChainBlock
import nl.tudelft.ipv8.sqldelight.Database
import java.util.*

private val blockMapper: (
    String,
    ByteArray,
    ByteArray,
    Long,
    ByteArray,
    Long,
    ByteArray,
    ByteArray,
    Long,
    Long,
    ByteArray
) -> TrustChainBlock = { type, tx, public_key, sequence_number, link_public_key,
                         link_sequence_number, previous_hash, signature, block_timestamp,
                         insert_time, _ ->
    TrustChainBlock(
        type,
        tx,
        public_key,
        sequence_number.toUInt(),
        link_public_key,
        link_sequence_number.toUInt(),
        previous_hash,
        signature,
        Date(block_timestamp),
        Date(insert_time)
    )
}

class TrustChainSQLiteStore(
    database: Database
) : TrustChainStore {
    private val dao = database.dbBlockQueries

    override fun addBlock(block: TrustChainBlock) {
        dao.addBlock(
            block.type,
            block.rawTransaction,
            block.publicKey,
            block.sequenceNumber.toLong(),
            block.linkPublicKey,
            block.linkSequenceNumber.toLong(),
            block.previousHash,
            block.signature,
            block.timestamp.time,
            Date().time,
            block.calculateHash()
        )
    }

    override fun get(publicKey: ByteArray, sequenceNumber: UInt): TrustChainBlock? {
        return dao.get(publicKey, sequenceNumber.toLong(), blockMapper).executeAsOneOrNull()
    }

    override fun getAllBlocks(): List<TrustChainBlock> {
        return dao.getAllBlocks(blockMapper).executeAsList()
    }

    override fun getBlockWithHash(blockHash: ByteArray): TrustChainBlock? {
        return dao.getBlockWithHash(blockHash, blockMapper).executeAsOneOrNull()
    }

    override fun getBlocksWithType(type: String): List<TrustChainBlock> {
        return dao.getBlocksWithType(type, blockMapper).executeAsList()
    }

    override fun contains(block: TrustChainBlock): Boolean {
        return get(block.publicKey, block.sequenceNumber) != null
    }

    override fun getLatest(publicKey: ByteArray, blockType: String?): TrustChainBlock? {
        return if (blockType != null) {
            dao.getLatestWithType(publicKey, blockType, publicKey,
                blockType, blockMapper).executeAsOneOrNull()
        } else {
            dao.getLatest(publicKey, publicKey, blockMapper).executeAsOneOrNull()
        }
    }

    override fun getLatestBlocks(
        publicKey: ByteArray,
        limit: Int,
        blockTypes: List<String>?
    ): List<TrustChainBlock> {
        return if (blockTypes != null) {
            dao.getLatestBlocksWithTypes(publicKey, blockTypes.joinToString(","),
                limit.toLong(), blockMapper).executeAsList()
        } else {
            dao.getLatestBlocks(publicKey, limit.toLong(), blockMapper).executeAsList()
        }
    }

    override fun getBlockAfter(block: TrustChainBlock, blockType: String?): TrustChainBlock? {
        return if (blockType != null) {
            dao.getBlockAfterWithType(block.sequenceNumber.toLong(), block.publicKey, blockType,
                blockMapper).executeAsOneOrNull()
        } else {
            dao.getBlockAfter(block.sequenceNumber.toLong(), block.publicKey, blockMapper)
                .executeAsOneOrNull()
        }
    }

    override fun getBlockBefore(block: TrustChainBlock, blockType: String?): TrustChainBlock? {
        return if (blockType != null) {
            dao.getBlockBeforeWithType(block.sequenceNumber.toLong(), block.publicKey, blockType,
                blockMapper).executeAsOneOrNull()
        } else {
            dao.getBlockBefore(block.sequenceNumber.toLong(), block.publicKey, blockMapper)
                .executeAsOneOrNull()
        }
    }

    override fun getLowestSequenceNumberUnknown(publicKey: ByteArray): Long {
        // The following query fetches the earliest block that does not have a subsequent block.
        // This does not work for the case where we are merely missing the first block, hence this
        // check.
        if (get(publicKey, 1u) == null) {
            return 1L
        }

        val latestSequenceNumber = dao.getLatestSequenceNumber(publicKey, publicKey)
            .executeAsOneOrNull()
        return if (latestSequenceNumber != null) latestSequenceNumber + 1 else 1
    }

    override fun getLowestRangeUnknown(publicKey: ByteArray): LongRange {
        val lowestUnknown = getLowestSequenceNumberUnknown(publicKey)
        val lowestKnownAfter = dao.getLowestSequenceNumberAfter(publicKey, lowestUnknown)
            .executeAsOneOrNull()
        val highestUnknown = if (lowestKnownAfter != null) lowestKnownAfter - 1 else lowestUnknown
        return LongRange(lowestUnknown, highestUnknown)
    }

    override fun getLinked(block: TrustChainBlock): TrustChainBlock? {
        return dao.getLinked(block.linkPublicKey, block.linkSequenceNumber.toLong(),
            block.publicKey, block.sequenceNumber.toLong(), blockMapper).executeAsOneOrNull()
    }

    override fun getAllLinked(block: TrustChainBlock): List<TrustChainBlock> {
        return dao.getAllLinked(block.linkPublicKey, block.linkSequenceNumber.toLong(),
            block.publicKey, block.sequenceNumber.toLong(), blockMapper).executeAsList()
    }

    override fun crawl(
        publicKey: ByteArray,
        startSeqNum: Long,
        endSeqNum: Long,
        limit: Int
    ): List<TrustChainBlock> {
        return dao.crawl(startSeqNum, endSeqNum, publicKey, limit.toLong(),
            startSeqNum, endSeqNum, publicKey, limit.toLong(), blockMapper).executeAsList()
    }

    override fun getRecentBlocks(limit: Int, offset: Int): List<TrustChainBlock> {
        return dao.getRecentBlocks(limit.toLong(), offset.toLong(), blockMapper).executeAsList()
    }

    override fun getUsers(limit: Int): List<UserInfo> {
        return dao.getUsers(limit.toLong()) { publicKey, count ->
            UserInfo(publicKey, count ?: 1)
        }.executeAsList()
    }

    override fun getMutualBlocks(publicKey: ByteArray, limit: Int): List<TrustChainBlock> {
        return dao.getMutualBlocks(publicKey, publicKey, limit.toLong(), blockMapper)
            .executeAsList()
    }

    override fun getBlockCount(publicKey: ByteArray?): Long {
        return if (publicKey != null) {
            dao.getBlockCountWithPublicKey(publicKey).executeAsOne()
        } else {
            dao.getBlockCount().executeAsOne()
        }
    }
}
