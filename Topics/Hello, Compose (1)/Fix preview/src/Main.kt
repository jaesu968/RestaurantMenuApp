@Preview
fun MessageInputPreview(text: String) {
    HyperskillTheme { 
        val textLength = text.trim().count()
        when {
            textLength < 1 -> Text(text = "The message text is empty.")
            textLength > 100 -> Text(text = "The message text is too long.")
            else -> Text(text = "Message sent!")
        }
    }
}