package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.improveenglishvocabularyapp.R
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.WordItem
import com.example.improveenglishvocabularyapp.databinding.WordRightAnswerBinding

class RightAnswersAdapter(
    private val onAddOrRemoveWordsToRememberDatabase: (position: Int) -> Unit
) : RecyclerView.Adapter<RightAnswersViewHolder>() {

    private var data: List<WordItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RightAnswersViewHolder {
        return RightAnswersViewHolder(
            binding = WordRightAnswerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RightAnswersViewHolder, position: Int) {

        val word = data.getOrNull(position)
        with(holder.binding) {
            answerNumber.text = (position + 1).toString()
            wordEnglish.text = "${word?.english.toString()} - "
            wordRussian.text = word?.russian.toString()
            addToDatabaseBtn.setOnClickListener { onAddOrRemoveWordsToRememberDatabase(position) }

        }
        if (word != null) {
            if (!word.isFavourite) {
                holder.binding.addToDatabaseBtn.setImageResource(R.drawable.add_word)
            } else {
                holder.binding.addToDatabaseBtn.setImageResource(R.drawable.remove_word)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<WordItem>) {
        this.data = data
        notifyDataSetChanged()
    }
}