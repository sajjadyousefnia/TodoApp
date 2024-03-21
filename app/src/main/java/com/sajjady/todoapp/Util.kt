package com.sajjady.todoapp

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

fun splitStringByWords(text: String, chunkSize: Int): List<String> {
    // Split the string into a list of words
    val words = text.split(" ")

    // Create a new list to store the chunks of words
    val chunks = mutableListOf<String>()

    // Iterate through the list of words, adding 50 words to a new chunk each time
    var currentChunk = ""
    var wordCount = 0
    for (word in words) {
        if (wordCount + word.length + 1 <= chunkSize) {
            // Add the word to the current chunk
            currentChunk += "$word "
            wordCount += word.length + 1
        } else {
            // Add the current chunk to the list and start a new one
            chunks.add(currentChunk)
            currentChunk = "$word "
            wordCount = word.length + 1
        }
    }

    // Add the last chunk to the list
    if (currentChunk.isNotEmpty()) {
        chunks.add(currentChunk)
    }

    return chunks
}

fun linesCalculator(fontSize: Int, wordsCount: Int) {
    val textStyle = TextStyle(fontSize = fontSize.sp) // Change 16.sp to your desired fontsize

}

fun checkIfNotEmpty(noteTitle: String, noteText: String): Boolean {
    if (noteTitle.isEmpty() || noteText.isEmpty()) {
        return false
    }
    if (noteTitle.isBlank() || noteText.isBlank()) {
        return false
    }
    return true
}
