package com.example.newsapp.ui.Main.Favourite.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.Utilities.FAV_TAG
import com.example.newsapp.Utilities.NOT_FAV_TAG
import com.example.newsapp.Utilities.loadImage
import com.example.newsapp.ui.Main.Favourite.ViewModel.HeadLinesLocalViewModel

class HeadLineNewsLocalAdapter(
    private val mViewModel: HeadLinesLocalViewModel
) :
    RecyclerView.Adapter<HeadLineNewsLocalAdapter.NewsRoomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRoomViewHolder {

        return NewsRoomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        )
    }

    override fun getItemCount(): Int = mViewModel.mLocalArticlesList.value?.count() ?: 0

    override fun onBindViewHolder(holder: NewsRoomViewHolder, position: Int) {


            val article = mViewModel.getLocalArticle(position)
            holder.titleTextView.text = article?.title
            holder.nameTextView.text = article?.source?.name
            holder.dateTextView.text = mViewModel.getFormattedDate(article?.publishedAt)
            holder.newsImage.loadImage(article?.urlToImage)
            holder.descriptionTextView.text = article?.description

            if (mViewModel.isFavourite(article!!)) {
                holder.favouriteImageButton.tag = FAV_TAG
                holder.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                holder.favouriteImageButton.tag = NOT_FAV_TAG
                holder.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }

            holder.nameTextView.setOnClickListener {
                article?.source?.id?.let {
                    mViewModel.mSourceObserver.value =
                        it
                }
            }

            holder.newsImage.setOnClickListener {
                mViewModel.navigateDetails.value = article?.url
            }

            holder.shareImageButton.setOnClickListener {
                mViewModel.mSharingObserver.value = article?.url
            }

            holder.favouriteImageButton.setOnClickListener {
                mViewModel.mRemoveFavourite.value = article
            }



    }


    inner class NewsRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val favouriteImageButton: ImageButton = itemView.findViewById(R.id.favouriteImageButton)
        val shareImageButton: ImageButton = itemView.findViewById(R.id.shareImageButton)
        val errorTextView: TextView = itemView.findViewById(R.id.errorPageImageView)
    }


}