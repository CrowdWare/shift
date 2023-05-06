package at.crowdware.drawercompose.ui.widgets

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import at.crowdware.drawercompose.R
import com.google.gson.GsonBuilder

fun readCountryData(context: Context): List<String> {
    val gson = GsonBuilder().create()
    val json = context.resources.openRawResource(R.raw.countries).bufferedReader().use { it.readText() }
    val countryList = gson.fromJson(json, Array<Country>::class.java)
    return countryList.map { it.Name }
}

data class Country(val Name: String, val Code: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownListbox(label: String, stateHolder: DropDownListboxStateHolder) {
    Column {
        Box {
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
            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = { stateHolder.onEnabled(false) },
                modifier=Modifier.width(with(LocalDensity.current){stateHolder.size.width.toDp()})
            ) {
                stateHolder.items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(s)},
                        onClick = {
                            stateHolder.onSelectedIndex(index)
                            stateHolder.onEnabled(false)
                        }
                    )
                }
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