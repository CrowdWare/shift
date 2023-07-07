package at.crowdware.shift.ui.pages

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.webkit.WebSettings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.R
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shiftapi.ui.theme.OnPrimary
import at.crowdware.shiftapi.ui.theme.Primary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

fun isUserOffline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val activeNetworkInfo: NetworkInfo? = connectivityManager?.activeNetworkInfo
    return activeNetworkInfo == null || !activeNetworkInfo.isConnected
}

@Composable
fun Home() {
    val lang = LocaleManager.getLanguage()
    val isOffline = isUserOffline(LocalContext.current)
    val context = LocalContext.current
    val crashFile = File(context.filesDir, "crash_report.txt")
    val crashMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (crashFile.exists()) {
            withContext(Dispatchers.IO) {
                val fileReader = FileReader(crashFile)
                try {
                    val bufferedReader = BufferedReader(fileReader)
                    var line: String?
                    while (bufferedReader.readLine().also { line = it } != null) {
                        withContext(Dispatchers.Main) {
                            crashMessage.value += line
                        }
                    }
                } catch (e: IOException) {
                    println("An error occurred reading the crash_report.txt: " + e.message)
                } finally {
                    fileReader.close()
                }
            }
        }
    }
    if (crashMessage.value.isNotEmpty()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            if (crashMessage.value.startsWith("Crash information for the app")) {
                Text(stringResource(R.string.please_send_app_crash_report), style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
            }else{
                Text(stringResource(R.string.please_send_plugin_crash_report), style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
            }

            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = crashMessage.value,
                onValueChange = {},
                readOnly = true,
                singleLine = false,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary,
            ), onClick = {
                crashFile.delete()
                crashMessage.value = ""
            }) {
                Text(stringResource(R.string.delete_report_button), style = TextStyle(fontSize = 20.sp))
            }
        }
    } else {
        if (isOffline) {
            AssetHtmlWebView("greeting-$lang.html")
        } else {
            OnlineWebView("http://shift.crowdware.at/greeting-$lang.html")
        }
    }
}

@Composable
fun AssetHtmlWebView(assetFileName: String) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .height(300.dp)) {
        item {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        loadAssetHtml(context, assetFileName)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun OnlineWebView(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                val webSettings = settings
                webSettings.cacheMode = WebSettings.LOAD_DEFAULT
                clearCache(true)
                loadUrl(url)
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .height(300.dp)
            .verticalScroll(rememberScrollState()) // Add verticalScroll modifier
    )
}

private fun WebView.loadAssetHtml(context: Context, assetFileName: String) {
    val fileUrl = "file:///android_asset/$assetFileName"
    loadUrl(fileUrl)
}