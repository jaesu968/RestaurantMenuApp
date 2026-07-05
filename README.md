# Restaurant Menu App

This project is an Android application built with Jetpack Compose designed to assist restaurant staff in managing orders.

## Stage 1: The Title

The goal of this first stage was to set up the project and display a simple title on the screen.

### Objectives
- Configure the project and Gradle to use JDK 11.
- Display a title on the screen with the exact text: `"Orders Menu"`.
- Ensure no extra whitespaces are present in the required strings.

## Stage 2: Main Menu with Features

For this stage, we expanded the UI to include the main menu header and the first menu item.

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
Modifiers allow you to decorate or augment composables. We used `Modifier.fillMaxSize()`, `Modifier.fillMaxWidth()`, and `Arrangement` to control positioning.

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

For this stage, we added interactivity to the menu by allowing users to select the quantity for each item.

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

For this stage, we expanded the menu by adding multiple recipes with different stock limits and made the `MenuItem` component reusable.

### Objectives
- Create a list or map of multiple recipes (e.g., Fettuccine, Risotto, Gnocchi, Spaghetti, Lasagna, Steak Parmigiana).
- Assign different stock limits for each recipe.
- Refactor the `MenuItem` composable to accept `name` and `amountStock` as parameters.
- Dynamically display all menu items on the screen.

### Key Concepts

#### 1. Reusable Composables
By passing parameters to a `@Composable` function, we can reuse the same UI structure for different data, reducing code duplication.

#### 2. Iterating in Compose
We can use standard Kotlin collection functions like `forEach` inside a layout composable to generate multiple child components dynamically.

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

## Stage 5: Make the Order (Planned)

The next step is to add a "Make Order" button that allows users to finalize their selection and see a summary of their order.

### Verification
To verify the implementation:
1. Run the application in an emulator or on a physical device.
2. Confirm that all menu items (Fettuccine, Risotto, etc.) are displayed.
3. Test the `+` and `-` buttons for different items and ensure they respect their individual stock limits.
4. Verify that the item names turn red when their respective maximum limit is reached.
5. Run the unit tests:
    - `Stage1UnitTest.kt`
    - `Stage2UnitTest.kt`
    - `Stage3UnitTest.kt`
    - `Stage4UnitTest.kt`
