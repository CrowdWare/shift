package at.crowdware.shift.logic

import nl.tudelft.ipv8.android.IPv8Android
import nl.tudelft.ipv8.keyvault.PublicKey
import java.lang.IllegalArgumentException

object BackendFacade {
    /**
     * Adds a transaction to the trustchain database, validates and signs it and sends it to the recipient
     *
     * @param amount The value in liter
     * @param purpose Reason to send transaction
     * @param from Name of the sender
     * @param publicKey The public key of the recipient
     */
    private fun addTransaction(amount: ULong, purpose: String, from: String, publicKey: ByteArray) {
        if (publicKey.contentEquals(IPv8Android.getInstance().myPeer.publicKey.keyToBin()))
            throw IllegalArgumentException("Transaction can not be send to self.")

        Backend.addTransactionToTrustChain(amount.toLong(), TransactionType.LMP, purpose, from, publicKey)
    }
}