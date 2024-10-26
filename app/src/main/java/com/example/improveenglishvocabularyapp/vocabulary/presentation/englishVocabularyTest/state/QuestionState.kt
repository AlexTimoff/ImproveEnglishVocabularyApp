package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.state


sealed class QuestionState {

    data object Loading: QuestionState()

    data class CurrentQuestion (val indexOfQuestion: Int): QuestionState()

    data class Error(val error: String): QuestionState()
}