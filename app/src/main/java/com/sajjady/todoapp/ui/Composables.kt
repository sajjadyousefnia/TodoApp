package com.sajjady.todoapp.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sajjady.todoapp.Database.Domain.Note
import com.sajjady.todoapp.OnAddNoteItemListener
import com.sajjady.todoapp.OnCopyNoteItemListener
import com.sajjady.todoapp.OnFloatingActionButtonClickListener
import com.sajjady.todoapp.SearchAppBarInterface
import com.sajjady.todoapp.R
import com.sajjady.todoapp.checkIfNotEmpty
import com.sajjady.todoapp.ui.theme.md_theme_light_onPrimaryContainer
import com.sajjady.todoapp.ui.theme.md_theme_light_onTertiary
import com.sajjady.todoapp.ui.theme.md_theme_light_primary
import com.sajjady.todoapp.ui.theme.md_theme_light_tertiary
import saman.zamani.persiandate.PersianDate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


@Composable
fun MyFloatingActionButton(floatingActionButtonClickListener: OnFloatingActionButtonClickListener) {
    FloatingActionButton(
        modifier = Modifier.padding(0.dp, 0.dp, 64.dp, 64.dp),
        onClick = {
            floatingActionButtonClickListener.showNoteDialog(true)
        },
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            /*
                        tint = whiteColor,
            */
            contentDescription = "Add",
        )
    }
}


/*
 IconButton(onClick = {}) {Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")}
*/
@Composable
fun TodoItem(
    context: Context, item: Note, index: Int, onCopyNoteItemListener: OnCopyNoteItemListener
) {
    Card(
        colors = CardColors(
            containerColor = md_theme_light_tertiary,
            contentColor = md_theme_light_onTertiary,
            disabledContainerColor = md_theme_light_tertiary,
            disabledContentColor = md_theme_light_onTertiary
        ),
        modifier = Modifier
            .padding(50.dp, 25.dp, 50.dp, 25.dp)
            .fillMaxWidth()
            .background(md_theme_light_primary, CircleShape)
    ) {
        Box(
            Modifier.wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.title,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 22.sp,
                        textDirection = TextDirection.Rtl,
                        fontFamily = FontFamily(Font(R.font.iransans_bold))
                    )
                )
                var threeDotsVisibility by remember { mutableStateOf(false) }
                var maxLines by remember { mutableStateOf(5) }
                // var lineCount by remember { mutableStateOf(0) }
                Box {
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        text = item.content,
                        maxLines = maxLines,
                        onTextLayout = { textLayoutResult ->
                            threeDotsVisibility =
                                textLayoutResult.hasVisualOverflow // Update the line count
                        },
                        style = TextStyle(
                            textDirection = TextDirection.Rtl,
                            textAlign = TextAlign.Justify,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.iransans_regular))
                        )
                    )
                }

                if (threeDotsVisibility) {
                    val vectorImage: ImageVector =
                        ImageVector.vectorResource(id = R.drawable.ic_three_dots)
                    IconButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(20.dp),
                        content = {
                            Icon(
                                imageVector = vectorImage,
                                contentDescription = "Long Text"
                            )
                        },
                        onClick = {
                            maxLines = Int.MAX_VALUE
                            threeDotsVisibility = false
                        },
                    )
                }
                val iconsModifier = Modifier.size(25.dp)
                val iconsContainerModifier = Modifier.padding(horizontal = 5.dp, vertical = 20.dp)
                val copyVectorImage: ImageVector =
                    ImageVector.vectorResource(id = R.drawable.ic_copy)
                val shareVectorImage: ImageVector =
                    ImageVector.vectorResource(id = R.drawable.ic_share)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Box(modifier = iconsContainerModifier) {
                        IconButton(
                            modifier = iconsModifier,
                            content = {
                                Icon(
                                    imageVector = copyVectorImage,
                                    contentDescription = "copy Text"
                                )
                            },
                            onClick = {
                                val clipboardManager =
                                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clipData = ClipData.newPlainText("text", item.content)
                                clipboardManager.setPrimaryClip(clipData)
                                onCopyNoteItemListener.showCopyPressedMessage("رونوشت متن با موفقیت تنظیم شد")
                            },
                        )
                    }
                    Box(modifier = iconsContainerModifier) {
                        IconButton(
                            modifier = iconsModifier,
                            content = {
                                Icon(
                                    imageVector = shareVectorImage,
                                    contentDescription = "share Text"
                                )
                            },
                            onClick = {

                                /*Create an ACTION_SEND Intent*/
                                val intent = Intent(Intent.ACTION_SEND)

                                /*This will be the actual content you wish you share.*/

                                /*The type of the content is text, obviously.*/
                                intent.setType("text/plain")

                                /*Applying information Subject and Body.*/
                                intent.putExtra(
                                    Intent.EXTRA_SUBJECT, item.title
                                )
                                intent.putExtra(Intent.EXTRA_TEXT, item.content)
                                context.startActivity(
                                    Intent.createChooser(
                                        intent, item.content
                                    )
                                )

                            },
                        )
                    }
                }
                Row {
                    var date = ""
                    val dateFormat: SimpleDateFormat =
                        SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
                    var parsedDate: Date? = null
                    try {
                        parsedDate = dateFormat.parse(item.time)
                        val persianDate: PersianDate = PersianDate(parsedDate)
                        date = persianDate.toString()
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        text = date,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            textIndent = TextIndent(10.sp),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.iransans_regular))
                        )
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        text = "تاریخ",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            textIndent = TextIndent(10.sp),
                            textDirection = TextDirection.Rtl,
                            textAlign = TextAlign.Justify,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.iransans_regular))
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun CustomDialog(
    context: Context,
    value: String,
    setShowDialog: (Boolean) -> Unit,
    setValue: (String) -> Unit,
    onAddNoteItemListChangeListener: OnAddNoteItemListener
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        var noteTitle by remember { mutableStateOf("") }
        var noteText by remember { mutableStateOf("") }
        var addButtonTextColor by remember { mutableStateOf(Color.LightGray) }
        var isEligibleToAdd by remember {
            mutableStateOf(false)
        }
        Surface(
            modifier = Modifier, shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier.wrapContentHeight(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(), text = "درج یادداشت", style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.iransans_bold)),
                            color = md_theme_light_primary,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    CustomNoteTextField(hint = "عنوان",
                        text = noteTitle,
                        isTitle = true,
                        onValueChangeLambda = { newText ->
                            noteTitle = newText
                            when (checkIfNotEmpty(noteTitle, noteText)) {
                                false -> {
                                    isEligibleToAdd = false
                                    addButtonTextColor = Color.LightGray
                                }

                                true -> {
                                    isEligibleToAdd = true
                                }
                            }
                        })
                    CustomNoteTextField(hint = "متن",
                        text = noteText,
                        isTitle = false,
                        onValueChangeLambda = { newText ->
                            noteText = newText
                            when (checkIfNotEmpty(noteTitle, noteText)) {
                                false -> {
                                    isEligibleToAdd = false
                                    addButtonTextColor = Color.LightGray
                                }

                                true -> {
                                    isEligibleToAdd = true
                                }
                            }
                        })
                    FilledIconButton(
                        modifier = Modifier.fillMaxWidth(), onClick = {
                            val note = Note(
                                time = Calendar.getInstance().getTime().toString(),
                                image = "",
                                status = "",
                                title = noteTitle,
                                category = "",
                                content = noteText,
                                priority = "",
                            )
                            if (isEligibleToAdd) {
                                onAddNoteItemListChangeListener.addNoteUpdateList(note)
                                onAddNoteItemListChangeListener.showSnackbar()
                                setShowDialog(false)
                            }
                        }, shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(), text = "افزودن", style = TextStyle(
                                color = addButtonTextColor,
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
fun CustomNoteTextField(
    hint: String, text: String, isTitle: Boolean, onValueChangeLambda: (String) -> Unit
) {
    val titleTextStyle = TextStyle(
        textDirection = TextDirection.Rtl,
        fontFamily = FontFamily(Font(R.font.iransans_regular)),
        color = md_theme_light_primary,
        textAlign = TextAlign.Right
    )
    TextField(
        value = text,
        onValueChange = onValueChangeLambda,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedPlaceholderColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
            focusedIndicatorColor = md_theme_light_primary,
            unfocusedIndicatorColor = md_theme_light_primary
        ),
        textStyle = titleTextStyle,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    width = 1.dp, color = md_theme_light_primary
                )
            )
            .onFocusEvent { },
        minLines = when (isTitle) {
            true -> 1
            false -> 3
        },
        maxLines = when (isTitle) {
            true -> 1
            false -> 10
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