package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.improveenglishvocabularyapp.R
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.WordItem
import com.example.improveenglishvocabularyapp.databinding.WordTranslationBinding


class QuestionVariantsAdapter(
    private val onItemClick: ((WordItem) -> Unit)): RecyclerView.Adapter<QuestionVariantsViewHolder>()  {

    private var data : List<WordItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionVariantsViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding = WordTranslationBinding.inflate(inflater,parent,false)
        return QuestionVariantsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionVariantsViewHolder, position: Int) {

        val word = data.getOrNull(position)
        with(holder.binding) {
            positionOfVariant.text = (position+1).toString()
            wordRus.text=word?.russian.toString()
            }

        holder.binding.root.setOnClickListener {
            if (word != null) {
                onItemClick.invoke(word)
            }
        }

        if (word != null){
            setColor(word,holder)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<WordItem>){
        this.data = data
        notifyDataSetChanged()
    }

    private fun setColor(word: WordItem, holder: QuestionVariantsViewHolder) {
        if (word.isClicked){
            holder.itemView.setBackgroundResource(R.drawable.selected_variant)
        }else{
            holder.itemView.setBackgroundResource(R.drawable.unselected_variant)
        }
        if (word.isRight){
            holder.itemView.setBackgroundResource(R.drawable.right_variant)
        }
        if (word.isWrong){
            holder.itemView.setBackgroundResource(R.drawable.wrong_variant)
        }
        if(word.isFrozenVariant){
            holder.itemView.setBackgroundResource(R.drawable.frozen_variant)
        }
    }
}