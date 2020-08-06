package com.example.newsapp.ui.Main.HeadLines.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Common.BaseFragment
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.R
import com.example.newsapp.Utilities.share
import com.example.newsapp.ui.Details.view.DetailsActivity
import com.example.newsapp.ui.Main.HeadLines.ViewModel.HeadLinesViewModel
import com.example.newsapp.ui.Main.MainActivity
import kotlinx.android.synthetic.main.fragment_network.view.*
import kotlinx.android.synthetic.main.fragment_network.view.toolbar
import kotlinx.android.synthetic.main.fragment_room.*


class HeadLineNewsFragment : BaseFragment() {

    override var layout: View? = null
    override var progressbarId: Int? = R.id.newsNetworkProgressBar
    lateinit var headLinesViewModel: HeadLinesViewModel
    lateinit var headLineNewsAdapter: HeadLineNewsAdapter
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

        layout = inflater.inflate(R.layout.fragment_network, container, false)

        setupViewModel()

        layout?.newsNetworkRecyclerView?.layoutManager = LinearLayoutManager(context)

        headLineNewsAdapter = HeadLineNewsAdapter(headLinesViewModel)

        layout?.newsNetworkRecyclerView?.adapter = headLineNewsAdapter

        headLinesViewModel.getDataFromNetwork()

        registerObservers(headLinesViewModel)

        super.onCreateView(inflater, container, savedInstanceState)


        return layout
    }

    private fun setupViewModel() {
        headLinesViewModel = ViewModelProvider(this).get(HeadLinesViewModel::class.java)
    }

    override fun registerObservers(viewModel: BaseViewModel) {
        super.registerObservers(viewModel)
        headLinesViewModel.articlesList.observe(viewLifecycleOwner, Observer {

            headLinesViewModel.mLoadingObserver.value = false
            if (!it.isNullOrEmpty()) {
                headLineNewsAdapter.notifyDataSetChanged()
            } else {
                headLinesViewModel.mShowErrorToast.value = R.string.app_name
            }

        })

        headLinesViewModel.navigateDetails.observe(viewLifecycleOwner, Observer {
            navigate(DetailsActivity::class.java, it)
        })

        headLinesViewModel.mSharingObserver.observe(viewLifecycleOwner, Observer {
            val intent = Intent(Intent.ACTION_SEND)
            intent.share(it)
            startActivity(Intent.createChooser(intent, "Share News"))
        })

        headLinesViewModel.mShowErrorToast.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context!!, getString(it), Toast.LENGTH_SHORT).show()
        })

        headLinesViewModel.mAddFavorite.observe(viewLifecycleOwner, Observer {
            headLinesViewModel.addNews(it)
        })

        headLinesViewModel.mRemoveFavourite.observe(viewLifecycleOwner, Observer {
            headLinesViewModel.deleteNews(it)
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

                if (newText.isEmpty()) {
                    headLinesViewModel.getDataFromNetwork()
                    return true
                }else{
                    headLinesViewModel.searchNewsNetwork(newText)
                }



                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

}