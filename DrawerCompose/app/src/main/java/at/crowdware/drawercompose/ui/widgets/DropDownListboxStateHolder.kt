package at.crowdware.drawercompose.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import at.crowdware.drawercompose.R

class DropDownListboxStateHolder(list: List<String>) {
    var enabled by mutableStateOf(false)
    var value by mutableStateOf("")
    var selectedIndex by mutableStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    val icon:Int
        @Composable get() = if (enabled){
            R.drawable.baseline_arrow_drop_up_24
        }else{
            R.drawable.baseline_arrow_drop_down_24
        }
    //val items = (1..5).map{
    //    "Option $it"
    //}
    val items = list
    fun onEnabled(newValue:Boolean){
        enabled= newValue
    }
    fun onSelectedIndex(newValue: Int){
        selectedIndex=newValue
        value = items[selectedIndex]
    }
    fun onSize(newValue: Size){
        size= newValue
    }
}

@Composable
fun rememberDropDownListboxStateHolder(items: List<String>) = remember {
    DropDownListboxStateHolder(items)
}