package at.crowdware.shift.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class GiveViewModel: ViewModel() {
    var balance = mutableStateOf(0L)
    /*var hours = mutableStateOf(0)
    var minutes = mutableStateOf(0)
    var total = mutableStateOf(0UL)
    var description = mutableStateOf("")
    var longNumber = mutableStateOf(0UL)
    var longNumberText = mutableStateOf("")
    */
}