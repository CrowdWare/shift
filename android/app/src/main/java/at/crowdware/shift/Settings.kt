package at.crowdware.shift

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
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast

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

    //region vars for the DropDownlistbox
    val context = LocalContext.current
    val languages = LocaleManager.getLanguages()
    val index = LocaleManager.getLanguageIndex()
    val currentActivity = LocalContext.current as? Activity
    val onSelectedIndexChanged: (Int) -> Unit = { idx ->
        LocaleManager.setLocale(context, idx)
        currentActivity?.recreate()
    }
    val stateHolderLanguage =
        rememberDropDownListboxStateHolder(languages, index, onSelectedIndexChanged)
    //endregion

    fun onDelete(item: Plugin) {
        val file = File(pluginPath + item.filename)
        println("to delete: ${file.path}")
        file.delete()
        Toast.makeText(context, context.getString(R.string.the_plugin_has_been_removed_you_should_restart_your_app_now), Toast.LENGTH_LONG).show()
    }

    var showFilePicker by remember { mutableStateOf(false) }
    val t1 = stringResource(R.string.the_plugin_has_been_installed_you_should_restart_your_app_now)
    val t2 = stringResource(R.string.there_was_an_error_copying_the_plugin)
    val t3 = stringResource(R.string.there_was_an_error_installing_the_plugin)

    FilePicker(showFilePicker, fileExtensions = listOf("apk") ) {
        if(it != null) {
            val uri = Uri.parse(it.path)
            val contentResolver: ContentResolver = applicationContext.contentResolver
            val pp = File(pluginPath)
            if(!pp.exists()) {
                pp.mkdir()
            }
            try {
                if(copyFileFromUri(contentResolver, uri, pp)) {
                    Toast.makeText(
                        context,
                        t1,
                        Toast.LENGTH_LONG
                    ).show()
                } else
                    Toast.makeText(context, t2, Toast.LENGTH_LONG).show()
            }
            catch (e: Exception) {
                Toast.makeText(context, t3, Toast.LENGTH_LONG).show()
            }

        }
        showFilePicker = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.settings), fontWeight = FontWeight.Bold,
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
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = { showFilePicker = true }) {
            Text("Install plugin")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview() {
    Settings()
}