package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.fragments

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.improveenglishvocabularyapp.databinding.FragmentFinishBinding
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModel
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var questionViewModelFactory: QuestionViewModelFactory
    private val viewModel: QuestionViewModel by activityViewModels {
        questionViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showResult()
        addAnimation()
        binding.tryAgain.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rightAnswers.setOnClickListener {
            val action = FinishFragmentDirections.actionFragmentFinishToFragmentRightAnswersList()
            findNavController().navigate(action)
        }
    }

    private fun showResult() {
        val userName = viewModel.name.value
        val numberOfQuestion = viewModel.numberOfQuestions.value
        val numberOfCorrectAnswers = viewModel.correctAnswers.value
        binding.name.text = userName.toString()
        binding.result.text = "Your score is $numberOfCorrectAnswers out of $numberOfQuestion!"
    }

    private fun addAnimation() {
        val numberOfQuestion = viewModel.numberOfQuestions.value?.toDouble()
        val numberOfCorrectAnswers = viewModel.correctAnswers.value.toDouble()
        val shareOfRightAnswers: Double = numberOfCorrectAnswers / numberOfQuestion!!
        if (shareOfRightAnswers >= 0.5) {
            congratulationAnimation()
        } else {
            lowRatedAnimation()
        }
    }

    private fun congratulationAnimation(){
        binding.congrats.visibility = View.VISIBLE
        binding.lottieAnim2.visibility = View.INVISIBLE
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f, 1f)
        ObjectAnimator.ofPropertyValuesHolder(binding.congrats, scaleX, scaleY).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        ObjectAnimator.ofArgb(
            binding.result,
            "textColor",
            Color.parseColor("#81377e"),
            Color.parseColor("#a51b0b")
        ).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
        }.start()
    }

    private fun lowRatedAnimation(){
        binding.lottieAnim.visibility = View.INVISIBLE
        binding.lottieAnim2.visibility = View.VISIBLE
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.2f)
        ObjectAnimator.ofPropertyValuesHolder(binding.tryAgain, scaleX, scaleY).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        ObjectAnimator.ofArgb(
            binding.result,
            "textColor",
            Color.parseColor("#4e1a18"),
            Color.parseColor("#a98783")
        ).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
        }.start()
    }
}