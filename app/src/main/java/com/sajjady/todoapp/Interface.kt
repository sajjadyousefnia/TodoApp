package com.sajjady.todoapp

import com.sajjady.todoapp.Database.Domain.Note

interface OnFloatingActionButtonClickListener {
    fun showNoteDialog(open: Boolean)
}

interface OnAddNoteItemListener {
    fun addNoteUpdateList(note: Note)
    fun showSnackbar()
}

interface OnListFlingListener {
    fun onListFlingDown()
    fun onListFlingUp()
}