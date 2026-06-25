# Restaurant Menu App - Stage 1

This project is an Android application built with Jetpack Compose designed to assist restaurant staff in managing orders.

## Stage 1: The Title

The goal of this first stage is to set up the project and display a simple title on the screen.

### Objectives
- Configure the project and Gradle to use JDK 11.
- Display a title on the screen with the exact text: `"Orders Menu"`.
- Ensure no extra whitespaces are present in the required strings.

### Key Concepts

#### 1. Jetpack Compose
Jetpack Compose is Android's modern toolkit for building native UI. It simplifies and accelerates UI development on Android using a declarative approach.

#### 2. Composables
Composables are the building blocks of Compose. They are functions marked with the `@Composable` annotation that define how the UI should look.
- **`Text`**: A basic Composable used to display text on the screen.
- **`Surface`**: A container that provides a background color and can apply elevation or clipping.

#### 3. Activity Setup
In a Compose-based Activity, `setContent` is used instead of `setContentView` to define the UI layout.

### Code Snippets

#### MainActivity.kt
The title is implemented using a custom Composable function called `ShowTitle`.

```kotlin
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
fun ShowTitle(title: String) {
    Text(text = title)
}
```

#### build.gradle (Task Module)
The project is configured to use Java 11 and enables the Compose feature.

```gradle
android {
    // ...
    buildFeatures {
        compose true
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = '11'
    }
}
```

### Verification
To verify the implementation:
1. Run the application in an emulator or on a physical device.
2. Ensure the text "Orders Menu" is visible on the screen.
3. Run the provided unit tests (`Stage1UnitTest.kt`) to check for exact matches.
