package org.hyperskill.ordersmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.hyperskill.ordersmenu.theme.PlayOrdersMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayOrdersMenuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowTitle("Orders Menu")
                }
            }
        }
    }
}

@Composable
fun ShowTitle(title: String){
    Text(text = title)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlayOrdersMenuTheme {
        ShowTitle("Orders Menu")
    }
}
