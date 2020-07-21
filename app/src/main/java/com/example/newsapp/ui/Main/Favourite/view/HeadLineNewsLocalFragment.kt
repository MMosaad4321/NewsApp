package com.example.newsapp.ui.Main.Favourite.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Common.BaseFragment
import com.example.newsapp.R
import com.example.newsapp.Utilities.share
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.ui.Details.view.DetailsActivity
import com.example.newsapp.ui.Main.Favourite.ViewModel.HeadLinesLocalViewModel
import com.example.newsapp.ui.Main.MainActivity
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.fragment_room.view.*
import kotlinx.android.synthetic.main.fragment_room.view.toolbar

class HeadLineNewsLocalFragment : BaseFragment() {

    override var layout: View? = null
    override var progressbarId: Int? = R.id.newsRoomProgressBar
    lateinit var headLinesLocalViewModel: HeadLinesLocalViewModel
    lateinit var headLineNewsLocalAdapter: HeadLineNewsLocalAdapter
    private var searchView: SearchView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout =inflater.inflate(R.layout.fragment_room,container,false)
        headLinesLocalViewModel = ViewModelProvider(this).get(HeadLinesLocalViewModel::class.java)

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
                if (headLinesLocalViewModel.firstLoad==0){
                    headLinesLocalViewModel.mLocalArticlesTemp.value = it
                    headLinesLocalViewModel.firstLoad+=1
                }
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
        })

        headLinesLocalViewModel.mPlaceHolderObserver.observe(viewLifecycleOwner, Observer {
            if(it){
                layout?.errorTextView?.visibility = View.VISIBLE
            }else{
                layout?.errorTextView?.visibility = View.GONE
            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                var articles = headLinesLocalViewModel.mLocalArticlesTemp.value
                if (newText.isEmpty()) {
                    headLinesLocalViewModel.mLocalArticlesList.value = headLinesLocalViewModel.mLocalArticlesTemp.value
                    return true
                }

                var filteredList = articles?.filter {
                    it.source?.name?.toLowerCase()?.contains(newText.toLowerCase()) ?: false
                }
                headLinesLocalViewModel.mLocalArticlesList.value = filteredList

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }


}