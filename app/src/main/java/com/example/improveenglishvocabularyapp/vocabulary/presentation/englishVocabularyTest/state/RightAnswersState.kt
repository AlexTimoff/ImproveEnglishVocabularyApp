package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.state

import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.WordItem


sealed class RightAnswersState {

    data object Loading: RightAnswersState()

    data class AllCorrectAnswers (val allCorrectAnswers: List<WordItem>): RightAnswersState()

    data class Error(val error: String): RightAnswersState()
}
