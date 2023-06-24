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

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import at.crowdware.shiftapi.R
import com.google.gson.GsonBuilder
import kotlin.math.min

fun readCountryData(context: Context): List<String> {
    val gson = GsonBuilder().create()
    val json = context.resources.openRawResource(R.raw.countries).bufferedReader().use { it.readText() }
    val countryList = gson.fromJson(json, Array<Country>::class.java)
    return countryList.map { it.Name }
}

data class Country(val Name: String, val Code: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownListbox(label: String, stateHolder: DropDownListboxStateHolder, modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        Box() {
            OutlinedTextField(
                value = stateHolder.value,
                readOnly = true,
                onValueChange = {},
                label = { Text(label) },
                trailingIcon = {
                    Icon(painter = painterResource(id = stateHolder.icon),
                        contentDescription = null,
                        Modifier.clickable {
                            stateHolder.onEnabled(!(stateHolder.enabled))
                        }
                    )
                },
                modifier = Modifier.onGloballyPositioned {
                    stateHolder.onSize(it.size.toSize())
                }
            )
            val width = with(LocalDensity.current) { stateHolder.size.width.toDp() }
            val height = with(LocalDensity.current) { stateHolder.size.height.toDp() }
            val maxHeight = LocalConfiguration.current.screenHeightDp.dp * 0.6f
            val itemHeight = 48.dp
            val contentHeight = stateHolder.items.size * itemHeight.value
            val boxHeight = min(contentHeight, maxHeight.value).dp
            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = { stateHolder.onEnabled(false) },
            ) {
                Box(modifier = Modifier.size(width = width, boxHeight)) {
                    LazyColumn {
                        items(stateHolder.items.size) { index ->
                            DropdownMenuItem(
                                text = { Text(stateHolder.items[index]) },
                                onClick = {
                                    stateHolder.onSelectedIndex(index)
                                    stateHolder.onEnabled(false) }
                            )
                        }
                    }
                }
            }
            Box(
                Modifier
                    .width(width)
                    .height(height)
                    .padding(top = 8.dp)
                    .clickable(onClick = { stateHolder.onEnabled(true) })
            ) {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountrySelectorPreview() {
    val countries = readCountryData(LocalContext.current.applicationContext)
    val stateHolder = rememberDropDownListboxStateHolder(countries)
    DropDownListbox(label = "Country", stateHolder = stateHolder)
}