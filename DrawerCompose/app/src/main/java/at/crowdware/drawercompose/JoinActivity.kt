package at.crowdware.drawercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class JoinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoinForm()
        }
    }
}

@Composable
fun JoinForm() {
    Text(text="Join")
}

@Preview(showSystemUi = true)
@Composable
fun JoinFormPreview()
{
    JoinForm()
}