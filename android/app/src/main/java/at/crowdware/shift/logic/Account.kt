package at.crowdware.shift.logic

import java.io.Serializable

data class Account(val name: String = "",
                   val uuid: String = "",
                   val ruuid: String = "",
                   val country: String = "",
                   val language: String = "",
                   var scooping: ULong = 0u,
                   var level_1_count: UInt = 0u,
                   var level_2_count: UInt = 0u,
                   var level_3_count: UInt = 0u,
                   var transactions: MutableList<Transaction> = mutableListOf<Transaction>()
) : Serializable
