package at.crowdware.shift.logic

import java.io.Serializable
import java.time.LocalDateTime

data class Transaction(var amount: ULong, val from: String, val date: LocalDateTime, var description: String):
    Serializable
