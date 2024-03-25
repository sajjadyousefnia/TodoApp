package com.sajjady.todoapp.ui.Notes

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.sajjady.todoapp.Database.Domain.Note
import com.sajjady.todoapp.Database.NoteDatabase
import com.sajjady.todoapp.NotesList
import com.sajjady.todoapp.OnAddNoteItemListener
import com.sajjady.todoapp.OnCopyNoteItemListener
import com.sajjady.todoapp.OnFloatingActionButtonClickListener
import com.sajjady.todoapp.OnListFlingListener
import com.sajjady.todoapp.SearchAppBarInterface
import com.sajjady.todoapp.R
import com.sajjady.todoapp.ui.CustomDialog
import com.sajjady.todoapp.ui.MyFloatingActionButton
import com.sajjady.todoapp.ui.NotesToolbar
import kotlinx.coroutines.delay

@Composable
fun NotesContainer(context: Context) {
    var snackbarMessage = ""
    var isSnackbarShown by remember {
        mutableStateOf(false)
    }
    var visibilityFAB by remember { mutableStateOf(true) }

    val db = NoteDatabase.getInstance(context)

    var items = remember {
        db.noteDao().getAll().toMutableStateList()
    }
    var searchText by remember {
        mutableStateOf("")
    }
    var isSearching by remember {
        mutableStateOf(false)
    }
    val isearchAppBarInterface = object : SearchAppBarInterface {
        override fun getSearchStatus() = isSearching
        override fun getSearchVariable() = searchText
        override fun setSearchVariable(searchVariable: String) {
            searchText = searchVariable
        }

        override fun changeSearchStatus() {
            isSearching = isSearching.not()
        }
    }


    val onBackPressedDispatcher: OnBackPressedDispatcher = OnBackPressedDispatcher()
    onBackPressedDispatcher.addCallback(
        context as LifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isSearching) {
                    isearchAppBarInterface.changeSearchStatus()
                }
            }
        })


    val onAddNoteItemListChangeListener: OnAddNoteItemListener =
        object : OnAddNoteItemListener {
            override fun addNoteUpdateList(note: Note) {
                NoteDatabase.getInstance(context).noteDao().insert(note)
                items.add(note)
            }

            override fun showSnackbar() {
                snackbarMessage = "یادداشت اضافه شد"
                isSnackbarShown = true
            }
        }

    var openDialog by remember { mutableStateOf(false) }
    var floatingActionButtonClickListener: OnFloatingActionButtonClickListener? = null
    floatingActionButtonClickListener =
        object : OnFloatingActionButtonClickListener {
            override fun showNoteDialog(open: Boolean) {
                openDialog = open
            }
        }
    Scaffold(
        snackbarHost =
        {
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
                        text = snackbarMessage,
                    )
                }

                false -> {}
            }
        },
        topBar =
        { NotesToolbar(isearchAppBarInterface) },
        floatingActionButton =
        {
            AnimatedVisibility(
                visible = visibilityFAB,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                MyFloatingActionButton(floatingActionButtonClickListener)
            }
        }, modifier = Modifier.fillMaxSize()
    )
    { innerPadding ->
        if (openDialog) {
            CustomDialog(context, "Sajjad Agha gole bagha", {
                openDialog = false
            }, {
                "sajjad"
            }, onAddNoteItemListChangeListener)
        }
        NotesList(
            context,
            items,
            innerPadding,
            onListFlingListener = object : OnListFlingListener {
                override fun onListFlingDown() {
                    visibilityFAB = false
                }

                override fun onListFlingUp() {
                    visibilityFAB = true
                }
            }, object : OnCopyNoteItemListener {
                override fun showCopyPressedMessage(message: String) {
                    snackbarMessage = message
                    isSnackbarShown = true
                }
            })
    }
}