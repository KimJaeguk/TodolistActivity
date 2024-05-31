package com.example.todolistactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistactivity.ui.theme.TodolistActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodolistActivityTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var text by remember { mutableStateOf("") }
    val itemList = remember { mutableStateListOf<String>() }
    val checkedStateList = remember { mutableStateListOf<Boolean>() }
    var isAllChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(32.dp)) {
        Row{
            Checkbox(
                checked = isAllChecked,
                onCheckedChange = { isChecked ->

                    isAllChecked = isChecked
                    checkedStateList.fill(isChecked)
                }
            )
            Text(text = "전체 선택")

        }
        val scrollstate = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(40.dp)
                .weight(1f)
                .verticalScroll(scrollstate)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            itemList.forEachIndexed { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Checkbox(
                        checked = checkedStateList.getOrElse(index) { false },
                        onCheckedChange = { isChecked ->
                            checkedStateList[index] = isChecked
                            isAllChecked = checkedStateList.all { it }
                        }
                    )
                    Text(text = item)
                }
            }
            Box(modifier = Modifier.height(100.dp)) {
            }
        }
        //등록 화면
        Row {
            TextField(value = text, onValueChange = { text = it })
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                itemList.add(text)
                checkedStateList.add(true)
                text = ""
            }) {
                Text(text = "등록", fontSize = 10.sp)

            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodolistActivityTheme {
        MainScreen()
    }
}