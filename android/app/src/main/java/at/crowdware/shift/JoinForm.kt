package at.crowdware.shift

import android.app.Activity
import android.content.Context
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
import androidx.core.app.ActivityCompat.recreate
import at.crowdware.shift.ui.widgets.DropDownListbox
import at.crowdware.shift.ui.widgets.readCountryData
import at.crowdware.shift.ui.widgets.rememberDropDownListboxStateHolder
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.PersistanceManager

data class JoinData(val name: String, val friend: String, val country: Int, val language: Int)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinForm(joinSuccessful: MutableState<Boolean>) {
    var context = LocalContext.current.applicationContext
    val joinData = PersistanceManager.readJoinData(context)
    println("country ${joinData.country}, lang ${joinData.language}")
    var errorMessage by remember { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf(joinData.name) }
    var friend by rememberSaveable { mutableStateOf(joinData.friend) }
    val countries = readCountryData(LocalContext.current.applicationContext)
    val language_codes = listOf("en", "de", "es", "fr", "pt", "eo")
    val currentActivity = LocalContext.current as? Activity
    val languages = listOf(
        stringResource(R.string.language_english),
        stringResource(R.string.language_german),
        stringResource(R.string.language_spanish),
        stringResource(R.string.language_french),
        stringResource(R.string.language_portugues),
        stringResource(R.string.language_esperanto)
    )
    var selectedLanguageCode by remember { mutableStateOf(LocaleManager.getLanguage()) }
    val stateHolderCountry = rememberDropDownListboxStateHolder(countries, joinData.country)
    val onSelectedIndexChanged: (Int) -> Unit = { index ->
        PersistanceManager.saveJoinData(context, name, friend, stateHolderCountry.selectedIndex, index)
        selectedLanguageCode = language_codes[index]
        LocaleManager.setLocale(context, selectedLanguageCode)
        currentActivity?.recreate()
    }
    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages, joinData.language, onSelectedIndexChanged)

    val onJoinFailed: (String?) -> Unit = { message ->
        if (message != null)
            errorMessage = message
    }

    val onJoinSucceed: () -> Unit = {
        errorMessage = ""
        joinSuccessful.value = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.join_shift)) },
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
            label = { Text(stringResource(R.string.name_or_nickname)) },
        )

        OutlinedTextField(
            value = friend,
            onValueChange = { friend = it },
            label = { Text(stringResource(R.string.invitation_code)) }
        )
        DropDownListbox(
            label = stringResource(R.string.select_your_country),
            stateHolder = stateHolderCountry)
        DropDownListbox(
            label = stringResource(R.string.select_preferred_language),
            stateHolder = stateHolderLanguage)
        Text(text = errorMessage, color = Color.Red)
        Button(
            onClick = {
                Backend.createAccount(
                    context, name, friend,
                    stateHolderCountry.value, stateHolderLanguage.value, onJoinSucceed, onJoinFailed
                )
            },
        ) {
            Text(stringResource(R.string.button_join_the_shift))
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

