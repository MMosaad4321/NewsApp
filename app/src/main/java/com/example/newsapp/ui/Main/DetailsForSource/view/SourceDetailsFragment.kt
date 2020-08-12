package com.example.newsapp.ui.Main.DetailsForSource.view

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
import com.example.newsapp.ui.Main.DetailsForSource.ViewModel.SourceDetailsViewModel
import com.example.newsapp.ui.Main.MainActivity
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.source_details_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SourceDetailsFragment :BaseFragment() {
    override var layout: View?= null
    override var progressbarId: Int? = R.id.sourceProgressBar
    lateinit var sourceAdapter : SourceAdapter

    private val sourceDetailsViewModel: SourceDetailsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layout = inflater.inflate(R.layout.source_details_fragment, container, false)


        val id = arguments?.getString("id")
        sourceDetailsViewModel.getDataFromNetwork(id)

        layout?.sourceRecyclerView?.layoutManager = LinearLayoutManager(context)

        sourceAdapter = SourceAdapter(sourceDetailsViewModel)

        layout?.sourceRecyclerView?.adapter = sourceAdapter

        registerObservers(sourceDetailsViewModel)
        super.onCreateView(inflater, container, savedInstanceState)
        return layout
    }

    override fun registerObservers(viewModel: BaseViewModel) {
        super.registerObservers(viewModel)
        sourceDetailsViewModel.articlesList.observe(viewLifecycleOwner, Observer {

            sourceDetailsViewModel.mLoadingObserver.value = false
            if (it.isNullOrEmpty()) {
                sourceAdapter.notifyDataSetChanged()
            }else{
                sourceDetailsViewModel.mShowErrorToast.value  = R.string.app_name
            }

        })

        sourceDetailsViewModel.navigateDetails.observe(viewLifecycleOwner, Observer {
            navigate(DetailsActivity::class.java, it)
        })

        sourceDetailsViewModel.mSharingObserver.observe(viewLifecycleOwner, Observer {
            val intent = Intent(Intent.ACTION_SEND)
            intent.share(it)
            startActivity(Intent.createChooser(intent, "Share News"))
        })

        sourceDetailsViewModel.mShowErrorToast.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
        })

        sourceDetailsViewModel.mAddFavorite.observe(viewLifecycleOwner, Observer {
            sourceDetailsViewModel.addNews(it)
        })

        sourceDetailsViewModel.mRemoveFavourite.observe(viewLifecycleOwner, Observer {
            sourceDetailsViewModel.deleteNews(it)
        })
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setSupportActionBar(toolbar)
    }




}