package com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits


data class Question (
    val askWord: WordItem,
    val translations: List<WordItem>,
    val rightAnswerWordId: Int
)
