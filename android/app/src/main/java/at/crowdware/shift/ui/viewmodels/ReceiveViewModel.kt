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
package at.crowdware.shift.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class ReceiveViewModel: ViewModel() {
    fun reset() {
        hours.value = 0
        minutes.value = 0
        total.value = 0L
        description.value = ""
        longNumber.value = 0L
        longNumberText.value = ""
    }

    var balance = mutableStateOf(0L)
    var hours = mutableStateOf(0)
    var minutes = mutableStateOf(0)
    var total = mutableStateOf(0L)
    var description = mutableStateOf("")
    var longNumber = mutableStateOf(0L)
    var longNumberText = mutableStateOf("")
}