package com.example.politi_cal.screens.NavDrawer

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.politi_cal.R


@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = Color(0xFFD7C488),
        contentColor = Color.Black,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Toggle drawer Menu")
            }
        })
}