package at.crowdware.shift.logic

import java.io.Serializable

data class Account(val name: String = "",
                   val uuid: String = "",
                   val ruuid: String = "",
                   val country: String = "",
                   val language: String = "",
                   var balance: Long = 0,
                   var scooping: ULong = 0u
) : Serializable
