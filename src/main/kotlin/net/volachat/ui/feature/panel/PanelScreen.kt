package net.volachat.ui.feature.panel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun PanelScreen (
    viewModel: PanelViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource("drawables/logo.png"),
                    modifier = Modifier.size(100.dp),
                    contentDescription = "Logo"
                )
            }
            Text(
                text = "VolaChat",
                style = MaterialTheme.typography.h3
            )
        }
    }
}