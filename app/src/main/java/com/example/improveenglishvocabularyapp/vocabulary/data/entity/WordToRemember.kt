package com.example.improveenglishvocabularyapp.vocabulary.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "allWordsToRemember")
data class WordToRemember (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "english")
    val english: String,

    @ColumnInfo(name = "russian")
    val russian: String,
)