package at.crowdware.shift.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class ReceiveViewModel: ViewModel() {
    fun reset() {
        hours.value = 0
        minutes.value = 0
        total.value = 0UL
        description.value = ""
        longNumber.value = 0UL
        longNumberText.value = ""

    }

    var balance = mutableStateOf(0UL)
    var hours = mutableStateOf(0)
    var minutes = mutableStateOf(0)
    var total = mutableStateOf(0UL)
    var description = mutableStateOf("")
    var longNumber = mutableStateOf(0UL)
    var longNumberText = mutableStateOf("")
}