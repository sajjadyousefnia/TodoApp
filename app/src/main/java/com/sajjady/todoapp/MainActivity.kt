package com.sajjady.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.sajjady.todoapp.Database.Domain.Note
import com.sajjady.todoapp.Database.NoteDatabase
import com.sajjady.todoapp.ui.CustomDialog
import com.sajjady.todoapp.ui.MyFloatingActionButton
import com.sajjady.todoapp.ui.MyToolbar
import com.sajjady.todoapp.ui.TodoItem
import com.sajjady.todoapp.ui.theme.TodoAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TodoAppTheme {
                var isSnackbarShown by remember {
                    mutableStateOf(false)
                }
                var visibilityFAB by remember { mutableStateOf(true) }

                val db = NoteDatabase.getInstance(this)

                val items = remember {
                    db.noteDao().getAll().toMutableStateList()
                }

                val onAddNoteItemListChangeListener: OnAddNoteItemListener =
                    object : OnAddNoteItemListener {
                        override fun addNoteUpdateList(note: Note) {
                            NoteDatabase.getInstance(this@MainActivity).noteDao().insert(note)
                            items.add(note)
                        }

                        override fun showSnackbar() {
                            isSnackbarShown = true
                        }
                    }

                var openDialog by remember { mutableStateOf(false) }
                var floatingActionButtonClickListener: OnFloatingActionButtonClickListener? = null
                floatingActionButtonClickListener = object : OnFloatingActionButtonClickListener {
                    override fun showNoteDialog(open: Boolean) {
                        openDialog = open
                    }
                }
                Scaffold(snackbarHost = {
                    when (isSnackbarShown) {
                        true -> Snackbar(
                            action = {
                                LaunchedEffect(Unit) {
                                    delay(2000)
                                    isSnackbarShown = false
                                }
                            },
                            modifier = Modifier.padding(30.dp, 0.dp),
                            dismissAction = {},
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.iransans_bold)),
                                ),
                                fontFamily = FontFamily(Font(R.font.iransans_bold)),
                                textAlign = TextAlign.Center,
                                text = "یادداشت اضافه شد",
                            )
                        }

                        false -> {}
                    }
                }, topBar = { MyToolbar() }, floatingActionButton = when (visibilityFAB) {
                    true -> {
                        {
                            MyFloatingActionButton(floatingActionButtonClickListener)
                        }
                    }

                    false -> {
                        {}
                    }

                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (openDialog) {
                        CustomDialog(this, "Sajjad Agha gole bagha", {
                            openDialog = false
                        }, {
                            "sajjad"
                        }, onAddNoteItemListChangeListener)
                    }
                    NotesList(items,
                        innerPadding,
                        onListFlingListener = object : OnListFlingListener {
                            override fun onListFlingDown() {
                                visibilityFAB = false
                            }

                            override fun onListFlingUp() {
                                visibilityFAB = true
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun NotesList(
    items: SnapshotStateList<Note>,
    innerPadding: PaddingValues,
    onListFlingListener: OnListFlingListener
) {
    // val lazyScrollState = rememberLazyListState()
    // val scrollState = rememberScrollState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (consumed.y < 0) {
                    onListFlingListener.onListFlingDown()
                } else if (consumed.y > 0) {
                    onListFlingListener.onListFlingUp()
                }
                return super.onPostFling(consumed, available)
            }
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth().nestedScroll(nestedScrollConnection), contentPadding = innerPadding
    ) {
        itemsIndexed(items) { index, item ->
            TodoItem(item, index)
        }
    }

}


/*
val فخخم = Modifier
    .padding(16.dp)
    .composed {


    }
    .background(Color.Red)
    .clickable { *//* Handle click event *//*
 }
    .then(Modifier.border(2.dp, Color.Black))

// val tooltipComposable =

fun Modifier.tooltip(): Modifier {
    return this. {
        (TooltipContent()){
            val tooltip = TooltipPopup()

        }
    }
}*/
