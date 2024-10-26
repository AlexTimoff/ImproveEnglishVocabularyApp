package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.Question
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.WordItem
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordDB
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.state.QuestionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class QuestionViewModel @Inject constructor(
    private val wordDao: WordDao
) : ViewModel() {

    private val _name = MutableStateFlow<String?>(null)
    val name: StateFlow<String?> = _name.asStateFlow()

    private val _numberOfQuestions = MutableStateFlow<Int?>(null)
    val numberOfQuestions: StateFlow<Int?> = _numberOfQuestions.asStateFlow()

    private val _allQuestions = MutableStateFlow<List<Question>?>(null)
    val allQuestions: StateFlow<List<Question>?> = _allQuestions.asStateFlow()

    private val _state = MutableStateFlow<QuestionState>(QuestionState.Loading)
    val state = _state.asStateFlow()

    private val _buttonSubmitState = MutableStateFlow<Boolean>(false)
    val buttonSubmitState = _buttonSubmitState.asStateFlow()

    private val _buttonContinueState = MutableStateFlow<Boolean>(false)
    val buttonContinueState = _buttonContinueState.asStateFlow()

    private val _correctAnswers = MutableStateFlow<Int>(0)
    val correctAnswers: StateFlow<Int> = _correctAnswers.asStateFlow()


    fun rememberName(nameOfUser: String) {
        viewModelScope.launch {
            _name.value = nameOfUser
        }
    }

    fun rememberNumberOfQuestions(numOfQuestion: Int) {
        viewModelScope.launch {
            _numberOfQuestions.value = numOfQuestion
        }
    }

    fun getAllQuestions(numberOfQuestion: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val questions = mutableListOf<Question>()
                var index = 0
                for (i in 1..numberOfQuestion) {
                    var oneQuestion = getSimpleQuestion()
                    var isInQuestions=checkDuplicatesQuestions(questions, oneQuestion)
                    while (isInQuestions) {
                        oneQuestion = getSimpleQuestion()
                        isInQuestions=checkDuplicatesQuestions(questions, oneQuestion)
                    }
                    questions.add(index, oneQuestion)
                    index++
                }
                _allQuestions.value = questions
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun checkDuplicatesQuestions(
        questions: MutableList<Question>,
        oneQuestion: Question
    ): Boolean {
        var isInList:Boolean = false
        for (question in questions) {
            if (question.askWord.id == oneQuestion.askWord.id) {
                isInList=true
                break
            } else {
                isInList=false
            }
        }
        return isInList
    }

    private suspend fun getSimpleQuestion(): Question {
        val wordsForQuestion = wordDao.getRandomWords(5)
        val askWord = wordsForQuestion.first().transformToWord()
        val rightAnswerWordId=askWord.id.toInt()
        val translations = createVariantsOfTranslations(wordsForQuestion)
        val correctAnswerPosition = Random.nextInt(translations.size + 1)
        translations.add(correctAnswerPosition, askWord)
        return Question(
            askWord = askWord,
            translations = translations,
            rightAnswerWordId=rightAnswerWordId
        )
    }

    fun showCurrentQuestion(index: Int) {
        viewModelScope.launch {
            _state.value = QuestionState.Loading
            try {
                delay(5_00)
                _state.value = QuestionState.CurrentQuestion(index)
            } catch (e: Throwable) {
                _state.value = QuestionState.Error(e.message.toString())
            }
        }
    }

    private fun createVariantsOfTranslations(data: List<WordDB>):MutableList<WordItem>{
        val allPossibleTranslations= data.subList(1, data.size)
            .map {
                it.transformToWord()
            }
            .toMutableList()
        return allPossibleTranslations
    }

    private fun WordDB.transformToWord(): WordItem {
        return WordItem(
            id = id,
            russian = russian,
            english = english,
            partOfSpeech = partOfSpeech,
        )
    }

    fun isEnableContinue(value: Boolean){
        viewModelScope.launch{
            _buttonContinueState.value=value
        }
    }

    fun isEnableSubmit(value: Boolean){
        viewModelScope.launch{
            _buttonSubmitState.value=value
        }
    }

    fun countCorrectAnswers(numberOfCorrectAnswers: Int){
        _correctAnswers.value=numberOfCorrectAnswers
    }
}