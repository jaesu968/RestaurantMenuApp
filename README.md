# Restaurant Menu App

This project is an Android application built with Jetpack Compose designed to assist restaurant staff in managing orders.

## Stage 1: The Title

The goal of this first stage was to set up the project and display a simple title on the screen.

### Objectives
- Configure the project and Gradle to use JDK 11.
- Display a title on the screen with the exact text: `"Orders Menu"`.
- Ensure no extra whitespaces are present in the required strings.

## Stage 2: Main Menu with Features

For this stage, I expanded the UI to include the main menu header and the first menu item.

### Objectives
- Center the title `"Orders Menu"` horizontally.
- Set the title font size to `48sp`.
- Add a menu item `"Fettuccine"` below the title.
- Set the menu item font size to `24sp`.
- Ensure elements are arranged vertically using a `Column`.

### Key Concepts

#### 1. Column and Row
- **`Column`**: A layout composable that places its children in a vertical sequence.
- **`Row`**: A layout composable that places its children in a horizontal sequence.

#### 2. Modifiers
Modifiers allow you to decorate or augment composables. I used `Modifier.fillMaxSize()`, `Modifier.fillMaxWidth()`, and `Arrangement` to control positioning.

### Code Snippets

#### MainActivity.kt (Stage 2)

```kotlin
@Composable
fun ShowTitle(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Text(text = title, fontSize = 48.sp)
    }
}
```

## Stage 3: Choose the Quantity

For this stage, I added interactivity to the menu by allowing users to select the quantity for each item.

### Objectives
- Add `+` and `-` buttons next to the menu item.
- Display the current quantity (starting at `0`).
- Implement a maximum limit of 5 items in stock.
- Change the menu item text color to **Red** when the maximum limit is reached.
- Prevent the quantity from going below `0`.

### Key Concepts

#### 1. State Management
- **`remember`**: Stores a value in the Composition. It helps retain state across the recompositions.
- **`mutableStateOf`**: A state holder that Compose observes. When its value changes, Compose automatically triggers a recomposition of the functions that read it.

#### 2. Clickable Modifier
- **`Modifier.clickable`**: Used to make a composable responsive to user input.

### Code Snippets

#### MainActivity.kt (Updated MenuItem)

```kotlin
@Composable
fun MenuItem() {
    var amountOrdered by remember { mutableStateOf(0) }
    val amountStock = 5
    val nameColor = if (amountOrdered == amountStock) Color.Red else Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Fettuccine", fontSize = 24.sp, color = nameColor)
        Text(
            text = "-",
            fontSize = 24.sp,
            modifier = Modifier.clickable { if (amountOrdered > 0) amountOrdered-- }
        )
        Text(text = "$amountOrdered", fontSize = 24.sp)
        Text(
            text = "+",
            fontSize = 24.sp,
            modifier = Modifier.clickable { if (amountOrdered < amountStock) amountOrdered++ }
        )
    }
}
```

## Stage 4: Add More Recipes

For this stage, I expanded the menu by adding multiple recipes with different stock limits and made the `MenuItem` component reusable.

### Objectives
- Create a list or map of multiple recipes (e.g., Fettuccine, Risotto, Gnocchi, Spaghetti, Lasagna, Steak Parmigiana).
- Assign different stock limits for each recipe.
- Refactor the `MenuItem` composable to accept `name` and `amountStock` as parameters.
- Dynamically display all menu items on the screen.

### Key Concepts

#### 1. Reusable Composables
By passing parameters to a `@Composable` function, I can reuse the same UI structure for different data, reducing code duplication.

#### 2. Iterating in Compose
I can use standard Kotlin collection functions like `forEach` inside a layout composable to generate multiple child components dynamically.

### Code Snippets

#### MainActivity.kt (Refactored MenuItem)

```kotlin
@Composable
fun MenuItem(name: String, amountStock: Int) {
    var amountOrdered by remember { mutableStateOf(0) }
    val nameColor = if (amountOrdered == amountStock) Color.Red else Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = name, fontSize = 24.sp, color = nameColor)
        Text(
            text = "-",
            fontSize = 24.sp,
            modifier = Modifier.clickable { if (amountOrdered > 0) amountOrdered-- }
        )
        Text(text = "$amountOrdered", fontSize = 24.sp)
        Text(
            text = "+",
            fontSize = 24.sp,
            modifier = Modifier.clickable { if (amountOrdered < amountStock) amountOrdered++ }
        )
    }
}
```

## Stage 5: Make the Order

For this stage, I added a "Make Order" button that finalizes the selection, shows an order summary in a `Toast`, and decreases the stock of each ordered item.

### Objectives
- Add a `Button` labeled `"Make Order"` (black background, white text, `24sp`) below the menu items.
- On click, build a summary message listing every ordered item in menu order:
  `Ordered:` followed by `==> <name>: <amount>` lines.
- Show the summary using a `Toast`.
- Decrease each item's stock by the amount ordered, then reset the order quantity back to `0`.
- Do nothing if no items were selected.
- Once an item's stock reaches `0`, it can no longer be ordered (name shows red at the limit).

### Key Concepts

#### 1. State Hoisting
In earlier stages, each `MenuItem` owned its own `amountOrdered` state. That doesn't work anymore because the "Make Order" button needs to read *all* quantities and update *all* stocks. The state was **hoisted** up to `MainActivity`, and `MenuItem` became a stateless component that receives values and reports changes through a callback:

```kotlin
@Composable
fun MenuItem(
    name: String,
    amountStock: Int,
    amountOrdered: Int,
    onUpdateOrder: (Int) -> Unit
)
```

#### 2. `mutableStateMapOf`
An observable map from Compose. Changing any entry (e.g., `recipesStock["Lasagna"] = 4`) automatically triggers recomposition of the composables reading it. Two maps hold the shared state:

```kotlin
val recipesStock = remember {
    mutableStateMapOf(
        "Fettuccine" to 5, "Risotto" to 6, "Gnocchi" to 4,
        "Spaghetti" to 3, "Lasagna" to 5, "Steak Parmigiana" to 2
    )
}
val recipesOrder = remember {
    mutableStateMapOf(
        "Fettuccine" to 0, "Risotto" to 0, "Gnocchi" to 0,
        "Spaghetti" to 0, "Lasagna" to 0, "Steak Parmigiana" to 0
    )
}
```

Note: state maps don't guarantee insertion order, so a separate `menuItems` list preserves the fixed display/order iteration order.

#### 3. Unidirectional Data Flow
State flows **down** (maps → `MenuItem` parameters) and events flow **up** (`onUpdateOrder`, `onOrderPlaced` callbacks). The `Toast` is triggered via a callback so the composable itself stays free of Android context concerns:

```kotlin
MakeOrderButton(
    menuItems = menuItems,
    recipesOrder = recipesOrder,
    recipesStock = recipesStock,
    onOrderPlaced = { msg ->
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
    }
)
```

#### 4. Building the Order Summary
Standard Kotlin collection operations (`map` + `filter`) select only the ordered items, then a `StringBuilder` assembles the required message format:

```kotlin
Button(
    onClick = {
        val orderedItems = menuItems
            .map { name -> name to (recipesOrder[name] ?: 0) }
            .filter { (_, amount) -> amount > 0 }
        if (orderedItems.isNotEmpty()) {
            val message = StringBuilder("Ordered:")
            orderedItems.forEach { (name, amount) ->
                message.append("\n==> $name: $amount")
                recipesStock[name] = (recipesStock[name] ?: 0) - amount  // update stock
                recipesOrder[name] = 0                                   // reset order
            }
            onOrderPlaced(message.toString())                            // show toast
        }
    },
    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Black,
        contentColor = Color.White
    )
) {
    Text(text = "Make Order", fontSize = 24.sp)
}
```

### Key Ideas
- **Single source of truth**: quantities and stock live in one place (`MainActivity`), so the button and every menu item always agree.
- **Stock as dynamic state**: stock is no longer a constant — after each order it shrinks, and the existing red-at-limit logic (`amountOrdered == amountStock`) naturally handles sold-out items (`0 == 0` is red immediately).
- **Deterministic ordering**: iterating over a fixed `menuItems` list (not the map) keeps the toast message and UI order consistent.

### Verification
To verify the implementation:
1. Run the application in an emulator or on a physical device.
2. Confirm that all menu items (Fettuccine, Risotto, etc.) are displayed.
3. Test the `+` and `-` buttons for different items and ensure they respect their individual stock limits.
4. Verify that the item names turn red when their respective maximum limit is reached.
5. Select quantities and press "Make Order" — a toast should list the ordered items and the stock should decrease accordingly.
6. Order an item until stock hits `0` and confirm it can no longer be ordered.
7. Run the unit tests:
    - `Stage1UnitTest.kt`
    - `Stage2UnitTest.kt`
    - `Stage3UnitTest.kt`
    - `Stage4UnitTest.kt`
    - `Stage5UnitTest.kt`
