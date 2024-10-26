package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.WordItem
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordToRemember
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.Question
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.state.RightAnswersState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RightAnswersListViewModel @Inject constructor(
    private val wordDao: WordDao
): ViewModel() {

    private val _state = MutableStateFlow<RightAnswersState>(RightAnswersState.Loading)
    val state = _state.asStateFlow()

    fun addWordToDatabase(wordItem: WordItem){
        viewModelScope.launch(Dispatchers.IO){
            val wordToRemember=transformIntoWordToRemember(wordItem)
            val isWordInDB=wordDao.isWordInDatabase(wordToRemember.id)
            if (isWordInDB==null) {
                wordDao.insert(wordToRemember)
            }
        }
    }

    fun removeWordFromDatabase(wordItem: WordItem){
        viewModelScope.launch(Dispatchers.IO){
            val wordToRemember=transformIntoWordToRemember(wordItem)
            val isWordInDB=wordDao.isWordInDatabase(wordToRemember.id)
            if (isWordInDB!=null) {
                wordDao.delete(wordToRemember)
            }
        }
    }

    fun checkRightAnswersIsFavourite(questions: List<Question>) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = RightAnswersState.Loading
            try {
                val answers = arrayListOf<WordItem>()
                for (question in questions) {
                    val askWord = question.askWord
                    val isAskWordInDB =
                        wordDao.isWordInDatabase(transformIntoWordToRemember(askWord).id)
                    if (isAskWordInDB != null) {
                        askWord.isFavourite = true
                    } else {
                        askWord.isFavourite = false
                    }
                    answers.add(askWord)
                }
                _state.value = RightAnswersState.AllCorrectAnswers(answers)
            } catch (e: Throwable) {
                _state.value = RightAnswersState.Error(e.message.toString())
            }
        }
    }

    private fun transformIntoWordToRemember(wordItem: WordItem): WordToRemember {
        val id=wordItem.id
        val english=wordItem.english
        val russian=wordItem.russian
        val wordToRemember= WordToRemember(id,english,russian)
        return wordToRemember
    }
}