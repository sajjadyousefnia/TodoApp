package com.sajjady.todoapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import com.sajjady.todoapp.Database.Domain.Note
import com.sajjady.todoapp.ui.Notes.NotesContainer
import com.sajjady.todoapp.ui.TodoItem
import com.sajjady.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


   /*     val onBackPressedDispatcher: OnBackPressedDispatcher = OnBackPressedDispatcher()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                    Toast.makeText(this@MainActivity, "back pressed", Toast.LENGTH_SHORT).show()
            }
        })*/

        setContent {
            TodoAppTheme {
                NotesContainer(context = this)
            }
        }
    }
}

@Composable
fun NotesList(
    context: Context,
    items: SnapshotStateList<Note>,
    innerPadding: PaddingValues,
    onListFlingListener: OnListFlingListener,
    onCopyNoteItemListener: OnCopyNoteItemListener
) {
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
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(nestedScrollConnection), contentPadding = innerPadding
    ) {
        itemsIndexed(items) { index, item ->
            TodoItem(context, item, index, onCopyNoteItemListener)
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
