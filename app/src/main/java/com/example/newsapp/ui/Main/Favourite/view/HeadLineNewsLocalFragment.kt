package com.example.newsapp.ui.Main.Favourite.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.Common.BaseFragment
import com.example.newsapp.R
import com.example.newsapp.Utilities.share
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.ui.Details.view.DetailsActivity
import com.example.newsapp.ui.Main.Favourite.ViewModel.HeadLinesLocalViewModel
import com.example.newsapp.ui.Main.MainActivity
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.fragment_room.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class HeadLineNewsLocalFragment : BaseFragment() {

    override var layout: View? = null
    override var progressbarId: Int? = R.id.newsRoomProgressBar
    val headLinesLocalViewModel: HeadLinesLocalViewModel by viewModel()
    lateinit var headLineNewsLocalAdapter: HeadLineNewsLocalAdapter




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout =inflater.inflate(R.layout.fragment_room,container,false)

        Glide.with(this).load(R.drawable.page_empty_page).into(layout?.errorPageImageView!!)

        layout?.newsRoomRecyclerView?.layoutManager = LinearLayoutManager(context)
        headLineNewsLocalAdapter =
            HeadLineNewsLocalAdapter(
                headLinesLocalViewModel
            )

        layout?.newsRoomRecyclerView?.adapter = headLineNewsLocalAdapter
        headLinesLocalViewModel.getDataFromRoom()



        registerObservers(headLinesLocalViewModel)
        super.onCreateView(inflater, container, savedInstanceState)

        return layout
    }

    override fun registerObservers(viewModel: BaseViewModel) {
        super.registerObservers(viewModel)
        headLinesLocalViewModel.mLocalArticlesList.observe(viewLifecycleOwner, Observer {
            headLinesLocalViewModel.mLoadingObserver.value = false
            if (!it.isNullOrEmpty()) {
                headLineNewsLocalAdapter.notifyDataSetChanged()
            }else{
                headLinesLocalViewModel.mShowErrorToast.value  = R.string.app_name
            }

        })

        headLinesLocalViewModel.navigateDetails.observe(viewLifecycleOwner, Observer {
            navigate(DetailsActivity::class.java, it)

        })

        headLinesLocalViewModel.mSharingObserver.observe(viewLifecycleOwner, Observer {
            val intent = Intent(Intent.ACTION_SEND)
            intent.share(it)
            startActivity(Intent.createChooser(intent, "Share News"))
        })

        headLinesLocalViewModel.mRemoveFavourite.observe(viewLifecycleOwner, Observer {
            headLinesLocalViewModel.deleteNews(it)
            headLinesLocalViewModel.getDataFromRoom()
        })

        headLinesLocalViewModel.mPlaceHolderObserver.observe(viewLifecycleOwner, Observer {
            if(it){
                layout?.errorPageImageView?.visibility = View.VISIBLE
            }else{
                layout?.errorPageImageView?.visibility = View.GONE
            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setSupportActionBar(toolbar)
    }



}