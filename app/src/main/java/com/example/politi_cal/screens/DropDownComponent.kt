package com.example.politi_cal.common


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


@Composable
fun dropDownMenu(list: List<String>, labeli: String, onSelected: (String) -> Unit = {}) {

    var expended by remember {
        mutableStateOf(false)
    }
    var selected by remember {
        mutableStateOf("")
    }

    var textFiledSize by remember {
        mutableStateOf(Size.Zero)
    } // this is the size of the text field

    val icon = if (expended) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(value = selected, onValueChange = {
            onSelected(it)
        }, modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFiledSize = coordinates.size.toSize()
            }, label = { Text(text = labeli) }, trailingIcon = {
            Icon(icon, "", Modifier.clickable { expended = !expended })
        })

        DropdownMenu(expanded = expended,
            onDismissRequest = { expended = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFiledSize.width.toDp() })
        ) {
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    selected = label
                    onSelected(label)
                    expended = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}