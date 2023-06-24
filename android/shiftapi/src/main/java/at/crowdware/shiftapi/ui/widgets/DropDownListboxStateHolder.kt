/****************************************************************************
 * Copyright (C) 2023 CrowdWare
 *
 * This file is part of SHIFT.
 *
 *  SHIFT is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SHIFT is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************/
package at.crowdware.shiftapi.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import at.crowdware.shiftapi.R

class DropDownListboxStateHolder(list: List<String>, index: Int, _onSelectedIndexChanged: ((Int) -> Unit)? = null) {
    var enabled by mutableStateOf(false)
    var value by mutableStateOf(if(index < 0){""} else { list[index] })
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