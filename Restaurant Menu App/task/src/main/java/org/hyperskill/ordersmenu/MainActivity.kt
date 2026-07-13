package org.hyperskill.ordersmenu

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hyperskill.ordersmenu.theme.PlayOrdersMenuTheme
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateMapOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlayOrdersMenuTheme {
                // Fixed menu order (state maps don't preserve insertion order)
                val menuItems = remember {
                    listOf(
                        "Fettuccine",
                        "Risotto",
                        "Gnocchi",
                        "Spaghetti",
                        "Lasagna",
                        "Steak Parmigiana"
                    )
                }
                // Define the state maps hers
                val recipesStock = remember {
                    mutableStateMapOf(
                        "Fettuccine" to 5,
                        "Risotto" to 6,
                        "Gnocchi" to 4,
                        "Spaghetti" to 3,
                        "Lasagna" to 5,
                        "Steak Parmigiana" to 2
                    )
                }
                val recipesOrder = remember {
                    mutableStateMapOf(
                        "Fettuccine" to 0,
                        "Risotto" to 0,
                        "Gnocchi" to 0,
                        "Spaghetti" to 0,
                        "Lasagna" to 0,
                        "Steak Parmigiana" to 0
                    )
                }
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    ShowTitle("Orders Menu")
                    // Display Menu Items in fixed menu order
                    menuItems.forEach { name ->
                        MenuItem(
                            name = name,
                            amountStock = recipesStock[name] ?: 0,
                            amountOrdered = recipesOrder[name] ?: 0,
                            onUpdateOrder = { recipesOrder[name] = it }
                        )
                    }

                // use the custom button composable
                MakeOrderButton(
                    menuItems = menuItems,
                    recipesOrder = recipesOrder,
                    recipesStock = recipesStock,
                    onOrderPlaced = { msg ->
                        Toast.makeText(this@MainActivity,
                            msg,
                            Toast.LENGTH_LONG).show()
                    })
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
    // variable to hold all menu items
    val recipesNameToStockAmount = mapOf(
        "Fettuccine" to 5,
        "Risotto" to 6,
        "Gnocchi" to 4,
        "Spaghetti" to 3,
        "Lasagna" to 5,
        "Steak Parmigiana" to 2
    )

    PlayOrdersMenuTheme {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ShowTitle("Orders Menu")
            recipesNameToStockAmount.forEach { (name, stock) ->
                MenuItem(name = "Fettuccine", amountStock = 5, amountOrdered = 0, onUpdateOrder = {})
            }
        }
    }
}

@Composable
fun MenuItem(name: String,
             amountStock: Int,
             amountOrdered: Int,
             onUpdateOrder: (Int) -> Unit
) {
    // Color logic: Red if stock limit is reached, otherwise Black
    val nameColor = if (amountOrdered == amountStock) Color.Red else Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Menu item name
        Text(
            text = name,
            fontSize = 24.sp,
            color = nameColor
        )
        // Decrease button "-"
        Text(
            text = "-",
            fontSize = 24.sp,
            modifier = Modifier.clickable {
                if (amountOrdered > 0) onUpdateOrder(amountOrdered - 1)
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
                if (amountOrdered < amountStock) onUpdateOrder(amountOrdered + 1)
            }
        )
    }
}

@Composable
fun MakeOrderButton(
    menuItems: List<String>,
    recipesOrder: MutableMap<String, Int>,
    recipesStock: MutableMap<String, Int>,
    onOrderPlaced: (String) -> Unit // Callback to show the toast: android.widget.Toast
){
    Button(
        onClick = {
            // Iterate in fixed menu order so every ordered item is listed consistently
            val orderedItems = menuItems
                .map { name -> name to (recipesOrder[name] ?: 0) }
                .filter { (_, amount) -> amount > 0 }
            if (orderedItems.isNotEmpty()) {
                val message = StringBuilder("Ordered:")
                orderedItems.forEach{ (name, amount) ->
                    // 1. Build the specific format required
                    message.append("\n==> $name: $amount")
                    // 2. Update the shared stock state
                    recipesStock[name] = (recipesStock[name] ?: 0) - amount
                    //3. Reset the order quantity for this item
                    recipesOrder[name] = 0
                }
                // 4. Trigger the toast via the callback
                onOrderPlaced(message.toString())
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(text = "Make Order", fontSize = 24.sp)
    }
}


