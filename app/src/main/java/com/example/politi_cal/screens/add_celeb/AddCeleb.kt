package com.example.politi_cal.screens.add_celeb

import android.R
import android.icu.text.AlphabeticIndex.Bucket.LabelType
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity

class AddCeleb : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            Surface(color=MaterialTheme.colors.background){
                init()
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun init(){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp))
    {
        Surface(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
        ) {
            Text(text = "Add Celebrity", fontSize = 40.sp)
        }
        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                var fname by remember { mutableStateOf("Enter the celeb first name") }
                OutlinedTextField(value = fname,
                    label = { Text(text = "First Name") },
                    onValueChange = { input -> fname = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "First Name"
                        )
                    })
            }
        }
        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                var lname by remember { mutableStateOf("Enter the celeb last name") }
                OutlinedTextField(value = lname,
                    label = { Text(text = "Last Name") },
                    onValueChange = { input -> lname = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Last Name"
                        )
                    })
            }
        }
        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                var desc by remember { mutableStateOf("Enter the celeb description") }
                OutlinedTextField(value = desc,
                    label = { Text(text = "Description") },
                    onValueChange = { input -> desc = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Description"
                        )
                    })
            }
        }

        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                var img by remember { mutableStateOf("Enter the celeb image") }
                OutlinedTextField(value = img,
                    label = { Text(text = "Image URL") },
                    onValueChange = { input -> img = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Face,
                            contentDescription = "Image"
                        )
                    })
            }
        }
        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                val categories = listOf<String>(
                    "Sport", "Journalism", "Academics",
                    "Culinary", "Entertainment", "Science"
                )
                var selection by remember { mutableStateOf(categories[0]) }
                var expanded by remember {
                    mutableStateOf(false)
                }
                ExposedDropdownMenuBox(expanded = expanded,
                    onExpandedChange = { expanded = !expanded })
                {
                    TextField(
                        readOnly = true,
                        value = selection,
                        onValueChange = { },
                        label = { Text(text = "Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        categories.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selection = selected
                                expanded = false
                            })
                            {
                                Text(text = selected)
                            }
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                val companies = listOf<String>(
                    "Maccabi Haifa soccer club", "Maccabi Tel Aviv Basketball club", "The Likud",
                    "N12 News", "Ariel University", "Mordechai from OS course"
                )
                var selectioncompany by remember { mutableStateOf(companies[0]) }
                var expanded by remember {
                    mutableStateOf(false)
                }
                ExposedDropdownMenuBox(expanded = expanded,
                    onExpandedChange = { expanded = !expanded })
                {
                    TextField(
                        readOnly = true,
                        value = selectioncompany,
                        onValueChange = { },
                        label = { Text(text = "Company") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        companies.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selectioncompany = selected
                                expanded = false
                            })
                            {
                                Text(text = selected)
                            }
                        }
                    }
                }
            }
        }

        OutlinedButton(onClick = { /*TODO*/ },
        colors=ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White
        )) {
            Text("Submit")
        }

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    init()
}
