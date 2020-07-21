package com.example.newsapp.ui.Main.DetailsForSource.view

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
import com.example.newsapp.ui.Main.DetailsForSource.ViewModel.SourceDetailsViewModel
import com.example.newsapp.ui.Main.HeadLines.view.HeadLineNewsAdapter

class SourceAdapter(
    val mViewModel: SourceDetailsViewModel
) :
    RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {

        return SourceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        )
    }

    override fun getItemCount(): Int  = mViewModel.articlesList.value?.count() ?: 0

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val article = mViewModel.getArticle(position)

        holder.titleTextView.text = article?.title
        holder.nameTextView.text = article?.source?.name
        holder.dateTextView.text = mViewModel.getFormattedDate(article?.publishedAt)
        holder.newsImage.loadImage(article?.urlToImage)
        holder.descriptionTextView.text = article?.description

        if (mViewModel.isFavourite(article!!)){
            holder.favouriteImageButton.tag = FAV_TAG
            holder.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_24)
        }else{
            holder.favouriteImageButton.tag = NOT_FAV_TAG
            holder.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
        }


        holder.newsImage.setOnClickListener {
            mViewModel.navigateDetails.value = article?.url
        }

        holder.shareImageButton.setOnClickListener {
            mViewModel.mSharingObserver.value = article?.url
        }

        holder.favouriteImageButton.setOnClickListener {
            if (holder.favouriteImageButton.tag == FAV_TAG){
                holder.favouriteImageButton.tag = NOT_FAV_TAG
                holder.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
                mViewModel.mRemoveFavourite.value = article
            }
            else{
                holder.favouriteImageButton.tag = FAV_TAG
                holder.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_24)
                mViewModel.mAddFavorite.value = article
            }
        }


    }


    inner class SourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val favouriteImageButton: ImageButton = itemView.findViewById(R.id.favouriteImageButton)
        val shareImageButton: ImageButton = itemView.findViewById(R.id.shareImageButton)

    }


}