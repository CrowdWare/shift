package at.crowdware.drawercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.crowdware.drawercompose.ui.theme.DrawerComposeTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalContext
import at.crowdware.drawercompose.ui.widgets.DropDownListbox
import at.crowdware.drawercompose.ui.widgets.readCountryData
import at.crowdware.drawercompose.ui.widgets.rememberDropDownListboxStateHolder
import com.google.gson.GsonBuilder

class JoinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoinForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinForm() {
    var name by rememberSaveable { mutableStateOf("") }
    var friend by rememberSaveable { mutableStateOf("") }
    val countries = readCountryData(LocalContext.current.applicationContext)
    val languages  = listOf("Deutsch", "English", "Español", "Français", "Português")
    val stateHolderCountry = rememberDropDownListboxStateHolder(countries)
    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages)

    DrawerComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

            Column(modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,) {

                CenterAlignedTopAppBar(
                    title = { Text("JOIN SHIFT") },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Row(Modifier.weight(1f)){
                Image(
                    painter = painterResource(id = R.drawable.icon_400x400),
                    contentDescription = stringResource(id = R.string.icon),
                    modifier = Modifier.fillMaxHeight(),
                )}
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name or Nickname") }
                )

                OutlinedTextField(
                    value = friend,
                    onValueChange = { friend = it },
                    label = { Text("ReferId  from a friend") }
                )
                DropDownListbox(label="Country",stateHolder = stateHolderCountry)
                DropDownListbox(label="Language",stateHolder = stateHolderLanguage)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { join(name, friend, stateHolderCountry.value, stateHolderLanguage.value) },
                ) {
                    Text("JOIN THE SHIFT")
                }
            }
        }
    }
}

fun join(name:String, friend: String, country: String, language: String){
    println("Create account ($name, $friend, $country, $language)")
}

@Preview(showSystemUi = true)
@Composable
fun JoinFormPreview()
{
    JoinForm()
}

