package net.volachat.ui.feature.panel

import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.volachat.App


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PanelScreen (viewModel: PanelViewModel) {
    Box() {
        TopAppBar(
            title = { Text(text = "VolaChat") },
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
            elevation = 8.dp,
            navigationIcon = {
                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource("drawables/logo.png"),
                        modifier = Modifier.size(70.dp),
                        contentDescription = "Logo"
                    )
                }
            }
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "Logged as "+App.token?.username ?: ""+"", modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp))
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Button(
                onClick = {
                    viewModel.logOut()
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFeb3b38)),
                modifier = Modifier.padding(8.dp)){
                Text(text = "Log out")
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
    ) {

        val state = rememberLazyListState()
        val itemCount = 100

        LazyColumn(Modifier.fillMaxSize().padding(start = 12.dp, end = 12.dp, top = 55.dp, bottom = 65.dp), state) {
            items(viewModel.messages) { x ->
                TextBox(x.message)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            label = { Text(text = "Send message") },
            onValueChange = {
                text = it
            },
            modifier = Modifier.padding(8.dp).fillMaxWidth().onPreviewKeyEvent {
                if (it.key == Key.Enter) {
                    when (it.type) {
                        KeyEventType.KeyUp -> {
                            viewModel.send(text.text)
                            text = TextFieldValue("")
                        }
                    }
                }
                false
            }
        )
    }
}



@Composable
private fun TextBox(text: String = "Item") {
    Surface(
        color = Color(135, 135, 135, 40),
        shape = RoundedCornerShape(4.dp)
    ) {
        Box(
            modifier = Modifier.height(32.dp)
                .fillMaxWidth()
                .padding(start = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = text)
        }
    }
}