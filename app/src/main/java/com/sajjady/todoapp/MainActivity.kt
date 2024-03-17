package com.sajjady.todoapp

import android.app.Dialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sajjady.todoapp.ui.CustomDialog
import com.sajjady.todoapp.ui.MyFloatingActionButton
import com.sajjady.todoapp.ui.MyToolbar
import com.sajjady.todoapp.ui.TodoItem
import com.sajjady.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                val openDialog = remember { mutableStateOf(false) }
                var floatingActionButtonClickListener: OnFloatingActionButtonClickListener? = null
                floatingActionButtonClickListener = object : OnFloatingActionButtonClickListener {
                    override fun showNewNoteDialog(open: Boolean) {
                        openDialog.value = open
                    }
                }
                Scaffold(topBar = { MyToolbar() }, floatingActionButton = {
                    MyFloatingActionButton(floatingActionButtonClickListener)
                }, modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (openDialog.value) {
                        CustomDialog("Sajjad Agha gole bagha", {
                            openDialog.value = false
                        }, {
                            "sajjad"
                        })
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(), contentPadding = innerPadding
                    ) {
                        val items = listOf("1", "2", "3")
                        itemsIndexed(items) { index, item ->
                            TodoItem()
                        }
                    }
                }
            }
        }
    }
}