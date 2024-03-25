package com.sajjady.todoapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.sajjady.todoapp.SearchAppBarInterface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesToolbar(iSearchAppBarInterface: SearchAppBarInterface) {

    when (iSearchAppBarInterface.getSearchStatus()) {
        true -> {
            SearchBar(query = iSearchAppBarInterface.getSearchVariable(),
                onQueryChange = { queryText ->
                    iSearchAppBarInterface.setSearchVariable(queryText)
                },
                onSearch = { value ->

                },
                active = true,
                onActiveChange = object : (Boolean) -> Unit {
                    override fun invoke(p1: Boolean) {
                    }
                },
                content = {

                })
        }

        false -> {
            TopAppBar(title = {
                Text("My App")
            }, navigationIcon = {
                IconButton(onClick = { /* Handle navigation icon click */ }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            }, actions = {
                IconButton(onClick = {
                    iSearchAppBarInterface.changeSearchStatus()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search, contentDescription = "Search"
                    )
                }

            }, colors = TopAppBarDefaults.topAppBarColors(
/*
                containerColor = primaryColor
*/
            )
            )

        }
    }
}
