package org.hyperskill.ordersmenu

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        ShowTitle("Orders Menu")
                        MenuItem()
                    }
                }
            }
        }
    }
}

@Composable
fun ShowTitle(title: String){
    Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top) {
        Text(text = title, fontSize = 48.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlayOrdersMenuTheme {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ShowTitle("Orders Menu")
            MenuItem()
        }
    }
}

@Composable
fun MenuItem() {
    // State management for the amount ordered quantity
    var amountOrdered by remember { mutableStateOf(0) } // default value is 0
    val amountStock = 5  // default stock limit is 5

    // Color logic: Red if stock limit is reached, otherwise Black
    val nameColor = if (amountOrdered == amountStock) Color.Red else Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Menu item name
        Text(
            text = "Fettuccine",
            fontSize = 24.sp,
            color = nameColor
        )
        // Decrease button "-"
        Text(
            text = "-",
            fontSize = 24.sp,
            modifier = Modifier.clickable {
                if (amountOrdered > 0) {
                    amountOrdered--
                }
            }
        )
        // Current quantity display
        Text(
            text = "$amountOrdered",
            fontSize = 24.sp
        )
        // Increase button "+"
        Text(
            text = "+",
            fontSize = 24.sp,
            modifier = Modifier.clickable {
                if (amountOrdered < amountStock) {
                    amountOrdered++
                }
            }
        )
    }


}
