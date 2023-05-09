package at.crowdware.shift.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import at.crowdware.shift.R

class DropDownListboxStateHolder(list: List<String>, index: Int, _onSelectedIndexChanged: ((Int) -> Unit)? = null) {
    var enabled by mutableStateOf(false)
    var value by mutableStateOf(list[index])
    var selectedIndex by mutableStateOf(index)
    var size by mutableStateOf(Size.Zero)
    val onSelectedIndexChanged = _onSelectedIndexChanged
    val icon:Int
        @Composable get() = if (enabled){
            R.drawable.baseline_arrow_drop_up_24
        }else{
            R.drawable.baseline_arrow_drop_down_24
        }
    val items = list
    fun onEnabled(newValue:Boolean){
        enabled= newValue
    }
    fun onSelectedIndex(newValue: Int){
        selectedIndex=newValue
        value = items[selectedIndex]
        onSelectedIndexChanged?.invoke(newValue)
    }
    fun onSize(newValue: Size){
        size= newValue
    }
}

@Composable
fun rememberDropDownListboxStateHolder(items: List<String>, index: Int = -1, onSelectedIndexChanged: ((Int) -> Unit)? = null) = remember {
    DropDownListboxStateHolder(items, index, onSelectedIndexChanged)
}