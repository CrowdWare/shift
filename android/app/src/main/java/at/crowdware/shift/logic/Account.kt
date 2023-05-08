package at.crowdware.shift.logic

import java.io.Serializable

data class Account(val name: String = "",
                   val uuid: String = "",
                   val ruuid: String = "",
                   val country: String = "",
                   val language: String = "",
                   var scooping: ULong = 0u,
                   var matescount: UInt = 0u,
                   var transactions: MutableList<Transaction> = mutableListOf<Transaction>()
) : Serializable
