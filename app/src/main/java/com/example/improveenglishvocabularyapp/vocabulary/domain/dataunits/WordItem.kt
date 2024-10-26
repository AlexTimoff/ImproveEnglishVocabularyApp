package com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits

data class WordItem (
    val id: Int,
    val english: String,
    val russian: String,
    val partOfSpeech: String,
    var isClicked:Boolean=false,
    var isRight:Boolean=false,
    var isWrong:Boolean=false,
    var isFrozenVariant:Boolean=false,
    var isFavourite:Boolean=false
)
