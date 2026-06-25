@Composable
fun ChatScreen(chatList: List<Chat>) {
    HyperskillTheme {
        chatList.forEach {
            ShowLastMessage(it.unread, it.message)
        }
    }
}

@Composable
fun LastMessage(message: String) {
    Text(text = "Last message: $message")
}

@Composable
fun LastUnreadMessage(message: String) {
    Text(text = "Last unread message: $message")
}

fun ShowLastMessage(showUnread: Boolean, message: String) {
    if (showUnread) {
        LastUnreadMessage(message)
    } else {
        LastMessage(message)
    }
}