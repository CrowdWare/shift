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
package at.crowdware.shift.ui.pages

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.Plugin
import at.crowdware.shift.logic.PluginManager
import at.crowdware.shift.ui.widgets.DropDownListbox
import at.crowdware.shift.ui.widgets.rememberDropDownListboxStateHolder
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import at.crowdware.shift.R
import at.crowdware.shift.ui.theme.OnPrimary
import at.crowdware.shift.ui.theme.Primary
import lib.Lib.getName
import lib.Lib.setName

fun getFileNameFromUri(contentResolver: ContentResolver, uri: Uri): String? {
    var fileName: String? = null
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex != -1) {
                fileName = it.getString(displayNameIndex)
            }
        }
    }
    return fileName
}

fun copyFileFromUri(contentResolver: ContentResolver, uri: Uri, destinationDirectory: File): Boolean {
    val inputStream = contentResolver.openInputStream(uri)
    inputStream?.use { input ->
        val fileName = getFileNameFromUri(contentResolver, uri)
        if (fileName != null) {
            val destinationPath = Paths.get(destinationDirectory.absolutePath, fileName)
            Files.copy(input, destinationPath, StandardCopyOption.REPLACE_EXISTING)
            return true
        } else {
          return false
        }
    }
    return false
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings() {
    val items = PluginManager.getPluginList()
    val pluginPath = LocalContext.current.filesDir.path + "/plugins/"
    val applicationContext = LocalContext.current.applicationContext
    val name = remember { mutableStateOf(getName()) }
    var saveButtonEnabled by remember { mutableStateOf(false) }
    val selectedFileUri = remember { mutableStateOf<Uri?>(null) }
    val filePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedFileUri.value = uri
    }

    //region vars for the DropDownlistbox
    val context = LocalContext.current
    val languages = LocaleManager.getLanguages()
    val index = LocaleManager.getLanguageIndex()
    val currentActivity = LocalContext.current as? Activity
    val onSelectedIndexChanged: (Int) -> Unit = { idx ->
        LocaleManager.setLocale(context, idx)
        currentActivity?.recreate()
    }
    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages, index, onSelectedIndexChanged)
    //endregion

    fun onDelete(item: Plugin) {
        val file = File(pluginPath + item.filename)
        println("to delete: ${file.path}")
        file.delete()
        Toast.makeText(context, context.getString(R.string.the_plugin_has_been_removed_you_should_restart_your_app_now), Toast.LENGTH_LONG).show()
    }

    val t1 = stringResource(R.string.the_plugin_has_been_installed_you_should_restart_your_app_now)
    val t2 = stringResource(R.string.there_was_an_error_copying_the_plugin)
    val t3 = stringResource(R.string.there_was_an_error_installing_the_plugin)

    LaunchedEffect(selectedFileUri.value) {
        if(selectedFileUri.value != null) {
            val contentResolver: ContentResolver = applicationContext.contentResolver
            val pp = File(pluginPath)
            if (!pp.exists()) {
                pp.mkdir()
            }
            try {
                if (copyFileFromUri(contentResolver, selectedFileUri.value!!, pp)) {
                    Toast.makeText(
                        context,
                        t1,
                        Toast.LENGTH_LONG
                    ).show()
                } else
                    Toast.makeText(context, t2, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, t3, Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.name_or_nickname),
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(value = name.value, onValueChange = {it ->
            name.value = it
            saveButtonEnabled = !name.value.isNullOrEmpty()
        })
        Spacer(modifier = Modifier.height(8.dp))
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary
        ),enabled = saveButtonEnabled, onClick = {
            setName(name.value)
            saveButtonEnabled = false
        }) {
            Text("Save Name")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.language), fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(4.dp))
        DropDownListbox(
            label = stringResource(R.string.select_preferred_language),
            stateHolder = stateHolderLanguage,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Plugins", fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(items) { item ->
                var deleted by remember { mutableStateOf(false) }

                AnimatedVisibility(
                    visible = !deleted,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    val dismissState = rememberDismissState(
                        confirmValueChange = { dismissValue ->
                            if(dismissValue == DismissValue.DismissedToStart) {
                                deleted = true
                                onDelete(item)
                                true
                            } else {
                                false
                            }
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val direction =
                                dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.DismissedToStart -> Color.Red
                                    else -> Color.LightGray
                                }
                            )
                            val alignment = when (direction) {
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                                else -> Alignment.CenterStart
                            }
                            val icon = Icons.Default.Delete
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        },
                        dismissContent = {
                            Card () {
                                ListItem(
                                    headlineContent = {
                                        Text(item.displayName, fontWeight = FontWeight.Bold)
                                    },
                                    supportingContent = { Text("Swipe left to uninstall.") },
                                    trailingContent = {Text(item.version)}
                                )
                                Divider()
                            }
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            ),
            onClick = {
                filePickerLauncher.launch("application/vnd.android.package-archive")
                }) {
            Text("Install plugin")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview() {
    Settings()
}