package at.crowdware.shift

import android.app.Activity
import android.widget.ScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.PersistanceManager
import at.crowdware.shift.ui.widgets.AutoSizeText
import at.crowdware.shift.ui.widgets.DropDownListbox
import at.crowdware.shift.ui.widgets.rememberDropDownListboxStateHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Intro(hasSeenDeleteWarning: MutableState<Boolean>) {
    var context = LocalContext.current.applicationContext
    val languages = listOf(
        stringResource(R.string.language_english),
        stringResource(R.string.language_german),
        stringResource(R.string.language_spanish),
        stringResource(R.string.language_french),
        stringResource(R.string.language_portugues),
        stringResource(R.string.language_esperanto)
    )
    val scrollState = rememberLazyListState()
    val currentActivity = LocalContext.current as? Activity
    val langIndex = PersistanceManager.getLanguageIndex(context)
    val language_codes = listOf("en", "de", "es", "fr", "pt", "eo")
    var selectedLanguageCode by remember { mutableStateOf(LocaleManager.getLanguage()) }
    val onSelectedIndexChanged: (Int) -> Unit = { index ->
        PersistanceManager.saveLanguageIndex(context, index)
        selectedLanguageCode = language_codes[index]
        LocaleManager.setLocale(context, selectedLanguageCode)
        currentActivity?.recreate()
    }
    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages, langIndex, onSelectedIndexChanged)
    val hasSeenAll = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.intro)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Row(Modifier.height(150.dp)) {
            Image(
                painter = painterResource(id = R.drawable.icon_400x400),
                contentDescription = stringResource(id = R.string.icon),
                modifier = Modifier.fillMaxHeight(),
            )
        }
        LazyColumn(modifier = Modifier.weight(1f), state = scrollState) {
            items(2) { index ->
                Text(
                    if (index == 0) { stringResource(R.string.uninstall_warning) } else {""},
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
            }
        }
        if( scrollState.layoutInfo.visibleItemsInfo.size < 2) {
            Text("...", style = TextStyle(fontSize = 20.sp))
        } else { hasSeenAll.value = true}
        Spacer(modifier = Modifier.height(16.dp))
        DropDownListbox(
            label = stringResource(R.string.select_preferred_language),
            stateHolder = stateHolderLanguage)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            PersistanceManager.putHasSeenDeleteWarning(context)
            hasSeenDeleteWarning.value = true
        },
        enabled = selectedLanguageCode.isNotEmpty() && hasSeenAll.value) {
            Text(stringResource(R.string.button_continue))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun IntroPreview()
{
    val hasSeenDeleteWarning = remember { mutableStateOf(false) }
    Intro(hasSeenDeleteWarning)
}
