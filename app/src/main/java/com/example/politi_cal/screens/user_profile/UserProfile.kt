package com.example.politi_cal.screens.user_profile


import android.R
import android.icu.text.AlphabeticIndex.Bucket.LabelType
import android.os.Bundle
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
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

class UserProfile : androidx.activity.ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                init()
            }
        }
    }
}


@Composable
fun init() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(text = "User Profile", fontSize = 40.sp)
        var username by remember {
            mutableStateOf("ofri")
        }
        var mail by remember {
            mutableStateOf("mail")
        }


        Surface(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(250.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome back " + username,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Select your favorite categories:",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        var sport_checked by remember {
            mutableStateOf(true)
        }

        var journalism_checked by remember {
            mutableStateOf(true)
        }

        var academics_checked by remember {
            mutableStateOf(true)
        }

        var culinary_checked by remember {
            mutableStateOf(true)
        }

        var entertainment_checked by remember {
            mutableStateOf(true)
        }

        var science_checked by remember {
            mutableStateOf(true)
        }

        val categories = listOf<String>(
            "Sport", "Journalism", "Academics",
            "Culinary", "Entertainment", "Science"
        )

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {


            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {


                //TODO: need to think how to make it changeable


                Checkbox(checked = sport_checked,
                    onCheckedChange = { sport_checked = !sport_checked })
                Text(text = categories[0], fontSize = 20.sp)

                Spacer(modifier = Modifier.size(10.dp))

                Checkbox(checked = journalism_checked,
                    onCheckedChange = { journalism_checked = !journalism_checked })
                Text(text = categories[1], fontSize = 20.sp)

            }
        }

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {


            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {


                //TODO: need to think how to make it changeable


                Checkbox(checked = academics_checked,
                    onCheckedChange = { academics_checked = !academics_checked })
                Text(text = categories[2], fontSize = 20.sp)

                Spacer(modifier = Modifier.size(10.dp))

                Checkbox(checked = culinary_checked,
                    onCheckedChange = { culinary_checked = !culinary_checked })
                Text(text = categories[3], fontSize = 20.sp)

            }
        }

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {


                //TODO: need to think how to make it changeable


                Checkbox(checked = entertainment_checked,
                    onCheckedChange = { entertainment_checked = !entertainment_checked })
                Text(text = categories[4], fontSize = 20.sp)

                Spacer(modifier = Modifier.size(10.dp))

                Checkbox(checked = science_checked,
                    onCheckedChange = { science_checked = !science_checked })
                Text(text = categories[5], fontSize = 20.sp)

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    init()
}
