package com.example.politi_cal.screens.NavDrawer

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.politi_cal.R


@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Toggle drawer Menu")
            }
        })
}