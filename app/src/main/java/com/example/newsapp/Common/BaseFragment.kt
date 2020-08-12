package com.example.newsapp.Common

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.newsapp.R
import com.example.newsapp.Utilities.URL_KEY
import com.example.newsapp.ui.Main.DetailsForSource.view.SourceDetailsFragment
import org.koin.core.KoinComponent

abstract class BaseFragment : Fragment(), KoinComponent {
    abstract var progressbarId: Int?
    abstract var layout: View?

    private var loadingProgressBar: ProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingProgressBar = progressbarId?.let { layout?.findViewById(it) }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun navigate(destination: Class<*>) {
        val intent = Intent(context, destination);
        startActivity(intent)
    }

    open fun navigate(destination: Class<*>, parameter: String) {
        val intent = Intent(context, destination)
        intent.putExtra(URL_KEY, parameter)
        startActivity(intent)
    }

    open fun registerObservers(viewModel: BaseViewModel) {

        viewModel.mLoadingObserver.observe(this, Observer {
            if (it)
                loadingProgressBar?.visibility = View.VISIBLE
            else
                loadingProgressBar?.visibility = View.GONE
        })

        viewModel.mSourceObserver.observe(viewLifecycleOwner, Observer {
            val bundle = Bundle()
            bundle.putString("id", it)
            val sourceDetailsFragment = SourceDetailsFragment()
            sourceDetailsFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, sourceDetailsFragment)
                ?.addToBackStack(null)
                ?.commit()
        })
    }


}