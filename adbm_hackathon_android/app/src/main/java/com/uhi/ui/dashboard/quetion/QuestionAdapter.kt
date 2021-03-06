package com.uhi.ui.dashboard.quetion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.uhi.BuildConfig
import com.uhi.R
import com.uhi.databinding.AnswerRadioButtonBinding
import com.uhi.databinding.ListItemQuestionBinding
import com.uhi.ui.common.base.BaseAdapter
import com.uhi.ui.common.model.Question
import com.uhi.utils.extention.*
import com.uhi.utils.glide.GlideRequests
import com.uhi.utils.glide.loadUrl


class QuestionAdapter(
    private val list: ArrayList<Question?> = arrayListOf(),
    private val glideRequests: GlideRequests,
    private val onClickListener: View.OnClickListener
) : BaseAdapter<Question>(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType != LIST_ITEM_PROGRESS) {
            return ViewHolder(parent.toBinding())
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when (holder) {
            is ViewHolder -> {
                list[position]?.let { holder.bind(it) }
            }
        }
    }

    fun add(question: Question?) {
        list.add(question)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListItemQuestionBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
        }

        fun bind(question: Question) = with(question) {
            binding.titleTextView.text = title
            binding.descriptionTextView.text = description
            binding.questionTextView.text = this.question
            glideRequests.loadUrl(binding.imageView, url = BuildConfig.IMAGE_URL+image, resPlaceHolderId = R.drawable.ic_place_holder)
            binding.answerLayout.removeAllViews()
            binding.answerLayout.generateAnswerLayout(this)?.let {
                binding.answerLayout.addView(it)
            }
        }
    }
}


