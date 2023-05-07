package at.crowdware.shift

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import at.crowdware.shift.ui.widgets.DropDownListbox
import at.crowdware.shift.ui.widgets.readCountryData
import at.crowdware.shift.ui.widgets.rememberDropDownListboxStateHolder
import at.crowdware.shift.logic.Backend


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinForm(joinSuccessful: MutableState<Boolean>) {
    var errorMessage by remember { mutableStateOf("") }
    var context = LocalContext.current.applicationContext
    var name by rememberSaveable { mutableStateOf("") }
    var friend by rememberSaveable { mutableStateOf("") }
    val countries = readCountryData(LocalContext.current.applicationContext)
    val languages = listOf("Deutsch", "English", "Español", "Français", "Português")
    val stateHolderCountry = rememberDropDownListboxStateHolder(countries)
    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages)
    val onJoinFailed: (String?) -> Unit = { message ->
        if (message != null)
            errorMessage = message
    }

    val onJoinSucceed: () -> Unit = {
        joinSuccessful.value = true
    }


    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CenterAlignedTopAppBar(
            title = { Text("JOIN SHIFT") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Row(Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = R.drawable.icon_400x400),
                contentDescription = stringResource(id = R.string.icon),
                modifier = Modifier.fillMaxHeight(),
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name or nickname") },
        )

        OutlinedTextField(
            value = friend,
            onValueChange = { friend = it },
            label = { Text("Refer-Id from inviter") }
        )
        DropDownListbox(label = "Select your country", stateHolder = stateHolderCountry)
        DropDownListbox(label = "Select preferred language", stateHolder = stateHolderLanguage)
        Text(text = errorMessage, color = Color.Red)
        Button(
            onClick = {
                Backend.createAccount(
                    context, name, friend,
                    stateHolderCountry.value, stateHolderLanguage.value, onJoinSucceed, onJoinFailed
                )
            },
        ) {
            Text("JOIN THE SHIFT")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun JoinFormPreview()
{
    val joinSuccessful = remember { mutableStateOf(false) }
    JoinForm(joinSuccessful)
}

