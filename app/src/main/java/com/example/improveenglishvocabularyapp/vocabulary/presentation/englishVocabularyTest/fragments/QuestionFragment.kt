package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.improveenglishvocabularyapp.databinding.FragmentQuestionBinding
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.Question
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.WordItem
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.state.QuestionState
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.adapter.QuestionVariantsAdapter
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModel
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    private var rightAnswerId: Int?=null
    private var questionIndex: Int=0
    private val questionVariantsAdapter = QuestionVariantsAdapter(::onClick)

    @Inject
    lateinit var questionViewModelFactory: QuestionViewModelFactory
    private val viewModel: QuestionViewModel by activityViewModels {
        questionViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = questionVariantsAdapter
        observeState()
        binding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 1,
                RecyclerView.VERTICAL,
                false)

        binding.btnSubmit.setOnClickListener {
            viewModel.isEnableSubmit(false)
            viewModel.isEnableContinue(true)
            checkAnswer()
        }

        binding.btnContinue.setOnClickListener {
            val numOfQuestions=viewModel.numberOfQuestions.value
            if (questionIndex < numOfQuestions?.minus(1)!!) {
                viewModel.showCurrentQuestion(questionIndex+1)
                viewModel.isEnableSubmit(false)
                viewModel.isEnableContinue(false)
            } else {
                val action = QuestionFragmentDirections.actionFragmentQuestionToFragmenFinish()
                findNavController().navigate(action)
            }
        }
        observeButtonContinueState()
        observeButtonSubmitState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {

                    is QuestionState.Loading -> {
                        with(binding) {
                            loader.visibility=View.VISIBLE
                        }
                    }

                    is QuestionState.CurrentQuestion -> {
                        with(binding) {
                            loader.visibility=View.GONE
                            questionIndex=state.indexOfQuestion
                            showQuestion(questionIndex)
                            showProgress(questionIndex)
                        }
                    }

                    is QuestionState.Error -> {
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showQuestion(index: Int) {
        val questions = viewModel.allQuestions.value
        val currentQuestion = questions?.get(index)
        binding.askQuestion.text = currentQuestion?.askWord?.english.toString()
        binding.partOfSpeech.text = "(${currentQuestion?.askWord?.partOfSpeech.toString()})"
        if (currentQuestion != null) {
            addVariantsOfTranslation(currentQuestion)
            rightAnswerId = currentQuestion.rightAnswerWordId
        }
    }

    private fun showProgress(index: Int) {
        val numOfQuestions=viewModel.numberOfQuestions.value
        val progress = index + 1
        val res = "$progress/$numOfQuestions"
        binding.progressBar.max = numOfQuestions!!
        binding.progressBar.progress = progress
        binding.tvProgress.text = res
    }

    private fun observeButtonContinueState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.buttonContinueState.collect{buttonState ->
                    binding.btnContinue.isEnabled=buttonState
                }
            }
        }
    }

    private fun observeButtonSubmitState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.buttonSubmitState.collect{buttonSubmitState ->
                    binding.btnSubmit.isEnabled=buttonSubmitState
                }
            }
        }
    }

    private fun addVariantsOfTranslation(question: Question) {
        val variants = question.translations
        questionVariantsAdapter.setData(variants)
    }

    private fun changeClickStatus(word: WordItem){
        val currentQuestion=viewModel.allQuestions.value?.get(questionIndex)
        if(currentQuestion!=null){
            val variants = currentQuestion.translations
            for (variant in variants){
                if(variant.id!=word.id && variant.isClicked){
                    variant.isClicked=false
                }
            }
            questionVariantsAdapter.setData(variants)
        }
    }

    private fun checkAnswer(){
        val currentQuestion=viewModel.allQuestions.value?.get(questionIndex)
        val correctAnswers=viewModel.correctAnswers.value
        if(currentQuestion!=null) {
            val variants = currentQuestion.translations
            for (variant in variants) {
                if (variant.isClicked && variant.id == rightAnswerId) {
                    variant.isRight = true
                    viewModel.countCorrectAnswers(correctAnswers + 1)
                } else if (variant.isClicked && variant.id != rightAnswerId) {
                    variant.isWrong = true
                } else if (!variant.isClicked && variant.id == rightAnswerId) {
                    variant.isRight = true
                } else if (!variant.isClicked && variant.id == rightAnswerId) {
                    variant.isRight = true
                } else {
                    variant.isFrozenVariant = true
                }
            }
            questionVariantsAdapter.setData(variants)
        }
    }

    private fun onClick(wordItem: WordItem){
        if (!wordItem.isClicked) {
            changeClickStatus(wordItem)
            wordItem.isClicked = true
            if ((!viewModel.buttonSubmitState.value && !viewModel.buttonContinueState.value) || (viewModel.buttonSubmitState.value && !viewModel.buttonContinueState.value)
            ) {
                viewModel.isEnableSubmit(true)
            } else {
                viewModel.isEnableSubmit(false)
            }
        } else {
            wordItem.isClicked = false
            viewModel.isEnableSubmit(false)
        }
        observeState()
    }
}



