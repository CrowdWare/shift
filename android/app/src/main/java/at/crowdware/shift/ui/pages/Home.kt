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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import at.crowdware.shift.logic.LocaleManager

fun isUserOffline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val activeNetworkInfo: NetworkInfo? = connectivityManager?.activeNetworkInfo

    return activeNetworkInfo == null || !activeNetworkInfo.isConnected
}

@Composable
fun Home() {
    val lang = LocaleManager.getLanguage()
    val isOffline = isUserOffline(LocalContext.current)

    if (isOffline) {
        AssetHtmlWebView("greeting-$lang.html")
    } else {
        OnlineWebView("http://shift.crowdware.at/greeting-$lang.html")
    }
}

@Composable
fun AssetHtmlWebView(assetFileName: String) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
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
            .verticalScroll(rememberScrollState()) // Add verticalScroll modifier
    )
}

private fun WebView.loadAssetHtml(context: Context, assetFileName: String) {
    val fileUrl = "file:///android_asset/$assetFileName"
    loadUrl(fileUrl)
}