package com.sajjady.todoapp.ui

import android.icu.text.CaseMap.Title
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.onFocusedBoundsChanged
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sajjady.todoapp.OnFloatingActionButtonClickListener
import com.sajjady.todoapp.R
import com.sajjady.todoapp.ui.theme.primaryColor
import com.sajjady.todoapp.ui.theme.whiteColor

@Composable
fun MyFloatingActionButton(floatingActionButtonClickListener: OnFloatingActionButtonClickListener) {
    FloatingActionButton(

        modifier = Modifier.padding(0.dp, 0.dp, 64.dp, 64.dp),
        onClick = {
            floatingActionButtonClickListener.showNewNoteDialog(true)
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            tint = whiteColor,
            contentDescription = "Add",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar() {
    TopAppBar(title = {
        Text("My App")
    }, navigationIcon = {
        IconButton(onClick = { /* Handle navigation icon click */ }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
        }
    }, actions = {
        IconButton(onClick = { /* Handle action icon click */ }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = primaryColor
    )
    )
}

@Composable
fun TodoItem() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = primaryColor
        ),
        modifier = Modifier
            .padding(50.dp, 25.dp, 50.dp, 25.dp)
            .height(100.dp)
            .fillMaxWidth()
            .background(primaryColor, CircleShape)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "عنوان",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = whiteColor, fontFamily = FontFamily(Font(R.font.iransans_bold))
                )
            )
            val checked = remember { mutableStateOf(false) }
            Checkbox(checked = checked.value, colors = CheckboxDefaults.colors(
                checkmarkColor = whiteColor
            ), onCheckedChange = { isChecked -> checked.value = isChecked })

        }
    }
}


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun CustomDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = Modifier, shape = RoundedCornerShape(16.dp), color = Color.White
        ) {
            Box(
                modifier = Modifier.wrapContentHeight(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    modifier = Modifier
                        .padding(20.dp)
                        .wrapContentHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(), text = "درج یادداشت", style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.iransans_bold)),
                            color = primaryColor,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    CustomNoteTextField(hint = "عنوان", isTitle = true)
                    CustomNoteTextField(hint = "متن", isTitle = false)
                    FilledIconButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }, shape = RoundedCornerShape(10.dp), colors = IconButtonColors(
                            containerColor = primaryColor,
                            contentColor = whiteColor,
                            disabledContainerColor = primaryColor,
                            disabledContentColor = whiteColor
                        )
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "افزودن",
                            style = TextStyle(
                                color = whiteColor,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.iransans_regular))
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomNoteTextField(hint: String, isTitle: Boolean) {
    val titleTextStyle = TextStyle(
        textDirection = TextDirection.Rtl,
        fontFamily = FontFamily(Font(R.font.iransans_regular)),
        color = primaryColor,
        textAlign = TextAlign.Right
    )
    var noteTitle by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = noteTitle.text,
        onValueChange = { newText: String ->
            noteTitle = noteTitle.copy(text = newText)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedPlaceholderColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
            focusedIndicatorColor = primaryColor,
            unfocusedIndicatorColor = primaryColor
        ),
        textStyle = titleTextStyle,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    width = 1.dp, color = primaryColor
                )
            )
            .onFocusEvent { },
        minLines = when (isTitle) {
            true -> 1
            false -> 3
        },
        placeholder = {
            Text(
                text = hint,
                style = titleTextStyle,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth()
            )
        },
    )
}
