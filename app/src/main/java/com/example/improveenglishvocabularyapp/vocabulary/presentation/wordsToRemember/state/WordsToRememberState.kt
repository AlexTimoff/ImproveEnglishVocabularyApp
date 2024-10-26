package com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.state

import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordToRemember

sealed class WordsToRememberState {

    data object Loading: WordsToRememberState()

    data class AllWordsToRemember (val allWordsToRemember: List<WordToRemember>): WordsToRememberState()

    data class Error(val error: String): WordsToRememberState()
}