package nl.tudelft.ipv8.attestation.trustchain

import nl.tudelft.ipv8.attestation.trustchain.payload.HalfBlockPayload
import nl.tudelft.ipv8.attestation.trustchain.store.TrustChainStore
import nl.tudelft.ipv8.attestation.trustchain.validation.ValidationErrors
import nl.tudelft.ipv8.attestation.trustchain.validation.ValidationResult
import nl.tudelft.ipv8.keyvault.*
import nl.tudelft.ipv8.util.sha256
import nl.tudelft.ipv8.util.toHex
import java.math.BigInteger
import java.util.*
import kotlin.Exception

@Suppress("DEPRECATION")
val GENESIS_HASH = ByteArray(32) { '0'.toByte() }
val GENESIS_SEQ = 1u
val UNKNOWN_SEQ = 0u
@Suppress("DEPRECATION")
val EMPTY_SIG = ByteArray(64) { '0'.toByte() }
@Suppress("DEPRECATION")
val EMPTY_PK = ByteArray(74) { '0'.toByte() }
val ANY_COUNTERPARTY_PK = EMPTY_PK

typealias TrustChainTransaction = Map<*, *>

/**
 * Container for TrustChain block information.
 */
class TrustChainBlock(
    /**
     * The block type name.
     */
    val type: String,

    /**
     * Transaction content.
     */
    val rawTransaction: ByteArray,

    /**
     * The serialized public key of the initiator of this block.
     */
    val publicKey: ByteArray,

    /**
     * The the sequence number of this block in the chain of the intiator of this block.
     */
    val sequenceNumber: UInt,

    /**
     * The serialized public key of the counterparty of this block.
     */
    val linkPublicKey: ByteArray,

    /**
     * The height of this block in the chain of the counterparty of this block, or 0 if unknown.
     */
    val linkSequenceNumber: UInt,

    /**
     * The hash of the previous block in the chain of the initiator of this block.
     */
    val previousHash: ByteArray,

    /**
     * The signature of the initiator of this block for this block.
     */
    var signature: ByteArray,

    /**
     * The time when this block was created.
     */
    val timestamp: Date,

    /**
     * The time when this block was inserted into the local database, if this block was inserted into
     * the local database.
     */
    val insertTime: Date? = null
) {
    val blockId = publicKey.toHex() + "." + sequenceNumber

    val linkedBlockId = linkPublicKey.toHex() + "." + linkSequenceNumber

    /**
     * Returns whether the block is a genesis block.
     */
    val isGenesis = sequenceNumber == GENESIS_SEQ && previousHash.contentEquals(GENESIS_HASH)

    /**
     * Returns whether the block is a self-signed block.
     */
    val isSelfSigned = publicKey.contentEquals(linkPublicKey)

    /**
     * Returns whether the block is a proposal block.
     */
    val isProposal = linkSequenceNumber == UNKNOWN_SEQ

    /**
     * Returns whether the block is an agreement block.
     */
    val isAgreement = linkSequenceNumber != UNKNOWN_SEQ

    val transaction: TrustChainTransaction by lazy {
        try {
            val (_, data) = TransactionEncoding.decode(rawTransaction)
            data as TrustChainTransaction
        } catch (e: TransactionSerializationException) {
            e.printStackTrace()
            mapOf<String, Any>()
        } catch (e: Exception) {
            e.printStackTrace()
            mapOf<String, Any>()
        }
    }

    /**
     * Return the hash of this block as a number (used as crawl ID).
     */
    val hashNumber: Int
        get() {
            val int = calculateHash().toHex().toBigInteger(16)
            return int.mod(BigInteger.valueOf(100000000)).toInt()
        }

    /**
     * Validates this block against what is known in the database.
     */
    fun validate(database: TrustChainStore): ValidationResult {
        // val blk = database.get(publicKey, sequenceNumber)
        // val link = database.getLinked(this)
        val prevBlk = database.getBlockBefore(this)
        val nextBlk = database.getBlockAfter(this)

        // Initialize the validation result to reflect the achievable validation level.
        var result = getMaxValidationLevel(prevBlk, nextBlk)

        // Check the block invariant.
        result = validateBlockInvariant(result)

        // TODO: Check if the linked block as retrieved from our database is the same as the one
        //  linked by this block. Detect double spend.

        // TODO: Check if the linked block as retrieved from our database is the same as the one
        //  linked by this block. Detect double countersign fraud.

        // Check if the chain of blocks is properly hooked up.
        result = validateChainConsistency(prevBlk, nextBlk, result)

        return result
    }

    /**
     * Determine the maximum validation level.
     *
     * Depending on the blocks we get from the database, we can decide to reduce the validation
     * level. We must do this prior to flagging any errors. This way we are only ever reducing
     * the validation level without having to resort to min()/max() every time we set it.
     */
    private fun getMaxValidationLevel(
        prevBlk: TrustChainBlock?,
        nextBlk: TrustChainBlock?
    ): ValidationResult {
        val isPrevGap = prevBlk == null || prevBlk.sequenceNumber != sequenceNumber - 1u
        val isNextGap = nextBlk == null || nextBlk.sequenceNumber != sequenceNumber + 1u

        return if (prevBlk == null && nextBlk == null && !isGenesis) {
            ValidationResult.NoInfo
        } else if (isPrevGap && isNextGap && !isGenesis) {
            ValidationResult.Partial
        } else if (isPrevGap && !isGenesis) {
            ValidationResult.PartialPrevious
        } else if (isNextGap) {
            ValidationResult.PartialNext
        } else {
            ValidationResult.Valid
        }
    }

    /**
     * Validate that the block is sane.
     */
    private fun validateBlockInvariant(prevResult: ValidationResult): ValidationResult {
        val errors = mutableListOf<String>()

        if (sequenceNumber < GENESIS_SEQ) {
            errors += ValidationErrors.INVALID_SEQUENCE_NUMBER
        }

        if (!defaultCryptoProvider.isValidPublicBin(publicKey)) {
            errors += ValidationErrors.INVALID_PUBLIC_KEY
        }

        if (!linkPublicKey.contentEquals(EMPTY_PK) &&
            !linkPublicKey.contentEquals(ANY_COUNTERPARTY_PK) &&
            !defaultCryptoProvider.isValidPublicBin(linkPublicKey)) {
            errors += ValidationErrors.INVALID_LINK_PUBLIC_KEY
        }

        try {
            val pk = defaultCryptoProvider.keyFromPublicBin(publicKey)
            val serialized = HalfBlockPayload.fromHalfBlock(this, false).serialize()
            if (!pk.verify(signature, serialized)) {
                errors += ValidationErrors.INVALID_SIGNATURE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (sequenceNumber == GENESIS_SEQ && !previousHash.contentEquals(GENESIS_HASH)) {
            errors += ValidationErrors.INVALID_GENESIS_HASH
        }

        if (sequenceNumber != GENESIS_SEQ && previousHash.contentEquals(GENESIS_HASH)) {
            errors += ValidationErrors.INVALID_GENESIS_SEQUENCE_NUMBER
        }

        return updateValidationResult(prevResult, errors)
    }

    /**
     * Check for chain order consistency.
     *
     * The previous block should point to us and this block should point to the next block.
     */
    private fun validateChainConsistency(
        prevBlk: TrustChainBlock?,
        nextBlk: TrustChainBlock?,
        prevResult: ValidationResult
    ): ValidationResult {
        val errors = mutableListOf<String>()

        if (prevBlk != null) {
            if (!prevBlk.publicKey.contentEquals(publicKey)) {
                errors += ValidationErrors.PREVIOUS_PUBLIC_KEY_MISMATCH
            }
            if (prevBlk.sequenceNumber >= sequenceNumber) {
                errors += ValidationErrors.PREVIOUS_SEQUENCE_NUMBER_MISMATCH
            }
            val isPrevGap = prevBlk.sequenceNumber != sequenceNumber - 1u
            if (!isPrevGap && !prevBlk.calculateHash().contentEquals(previousHash)) {
                errors += ValidationErrors.PREVIOUS_HASH_MISMATCH
                // Is this fraud? It is certainly an error, but fixing it would require a different
                // signature on the same sequence number which is fraud.
            }
        }

        if (nextBlk != null) {
            if (!nextBlk.publicKey.contentEquals(publicKey)) {
                errors += ValidationErrors.NEXT_PUBLIC_KEY_MISMATCH
            }
            if (nextBlk.sequenceNumber <= sequenceNumber) {
                errors += ValidationErrors.NEXT_SEQUENCE_NUMBER_MISMATCH
            }
            val isNextGap = nextBlk.sequenceNumber != sequenceNumber + 1u
            if (!isNextGap && !nextBlk.previousHash.contentEquals(calculateHash())) {
                errors += ValidationErrors.NEXT_HASH_MISMATCH
                // Again, this might not be fraud, but fixing it can only result in fraud.
            }
        }

        return updateValidationResult(prevResult, errors)
    }

    private fun updateValidationResult(prevResult: ValidationResult, newErrors: List<String>): ValidationResult {
        return if (newErrors.isNotEmpty()) {
            val prevErrors = if (prevResult is ValidationResult.Invalid) {
                prevResult.errors
            } else listOf()
            val errors = prevErrors + newErrors
            ValidationResult.Invalid(errors)
        } else {
            prevResult
        }
    }

    /**
     * Signs this block with the given key.
     *
     * @param key The key to sign this block with.
     */
    fun sign(key: PrivateKey) {
        val payload = HalfBlockPayload.fromHalfBlock(this, sign = false).serialize()
        signature = key.sign(payload)
    }

    fun calculateHash(): ByteArray {
        val payload = HalfBlockPayload.fromHalfBlock(this).serialize()
        return sha256(payload)
    }

    override fun equals(other: Any?): Boolean {
        return other is TrustChainBlock && other.calculateHash().contentEquals(calculateHash())
    }

    override fun hashCode(): Int {
        return calculateHash().contentHashCode()
    }

    class Builder(
        var type: String? = null,
        var rawTransaction: ByteArray? = null,
        var publicKey: ByteArray? = null,
        var sequenceNumber: UInt? = null,
        var linkPublicKey: ByteArray? = null,
        var linkSequenceNumber: UInt? = null,
        var previousHash: ByteArray? = null,
        var signature: ByteArray? = null
    ) {
        fun build(): TrustChainBlock {
            val type = type
                ?: throw IllegalStateException("type is null")
            val rawTransaction = rawTransaction
                ?: throw IllegalStateException("transaction is null")
            val publicKey = publicKey
                ?: throw IllegalStateException("public key is null")
            val sequenceNumber = sequenceNumber
                ?: throw IllegalStateException("sequence number is null")
            val linkPublicKey = linkPublicKey
                ?: throw IllegalStateException("link public key is null")
            val linkSequenceNumber = linkSequenceNumber
                ?: throw IllegalStateException("link sequence number is null")
            val previousHash = previousHash
                ?: throw IllegalStateException("previous hash is null")
            val signature = signature
                ?: throw IllegalStateException("signature is null")
            return TrustChainBlock(type,
                rawTransaction,
                publicKey,
                sequenceNumber,
                linkPublicKey,
                linkSequenceNumber,
                previousHash,
                signature,
                Date())
        }
    }
}
