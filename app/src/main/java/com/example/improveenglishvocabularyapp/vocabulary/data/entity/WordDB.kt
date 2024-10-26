package com.example.improveenglishvocabularyapp.vocabulary.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabulary")
data class WordDB (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "english")
    val english: String,

    @ColumnInfo(name = "russian")
    val russian: String,

    @ColumnInfo(name = "partOfSpeech")
    val partOfSpeech: String,
)
