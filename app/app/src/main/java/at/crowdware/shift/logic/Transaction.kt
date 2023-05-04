package at.crowdware.shift.logic

import java.io.Serializable
import java.util.Date

data class Transaction(val amount: Long, val from: String, val date: Date, val description: String):
    Serializable
