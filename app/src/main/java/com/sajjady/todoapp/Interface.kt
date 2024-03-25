package com.sajjady.todoapp

import com.sajjady.todoapp.Database.Domain.Note

interface OnFloatingActionButtonClickListener {
    fun showNoteDialog(open: Boolean)
}

interface OnAddNoteItemListener {
    fun addNoteUpdateList(note: Note)
    fun showSnackbar()
}

interface OnCopyNoteItemListener {
    fun showCopyPressedMessage(message: String)
}

interface OnListFlingListener {
    fun onListFlingDown()
    fun onListFlingUp()
}

interface SearchAppBarInterface {
    public fun getSearchStatus(): Boolean
    fun getSearchVariable(): String
    fun setSearchVariable(searchVariable: String)
    fun changeSearchStatus()
}