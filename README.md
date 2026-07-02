# Restaurant Menu App

This project is an Android application built with Jetpack Compose designed to assist restaurant staff in managing orders.

## Stage 1: The Title

The goal of this first stage was to set up the project and display a simple title on the screen.

### Objectives
- Configure the project and Gradle to use JDK 11.
- Display a title on the screen with the exact text: `"Orders Menu"`.
- Ensure no extra whitespaces are present in the required strings.

## Stage 2: Main Menu with Features

In this stage, we expanded the UI to include the main menu header and the first menu item.

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

#### MainActivity.kt

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

@Composable
fun MenuItem() {
    Text(text = "Fettuccine", fontSize = 24.sp)
}
```

## Stage 3: Choose the Quantity (Planned)

The next step is to add interactivity by allowing users to select the quantity for each menu item.

### Objectives
- Add `+` and `-` buttons next to the menu item.
- Display the current quantity (starting at `0`).
- Implement a maximum limit (e.g., 5 items in stock).
- Change the menu item text color to **Red** when the maximum limit is reached.
- Prevent the quantity from going below `0`.

### Verification
To verify the implementation:
1. Run the application in an emulator or on a physical device.
2. Ensure the text "Orders Menu" is centered and "Fettuccine" is visible.
3. Run the unit tests:
    - `Stage1UnitTest.kt`
    - `Stage2UnitTest.kt`
    - `Stage3UnitTest.kt` (once implemented)
