package at.vinatzer.githubsearch

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.vinatzer.githubsearch.adapter.ResultAdapter
import at.vinatzer.githubsearch.databinding.FragmentMainBinding

class MainFragment: Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var repoAdapter: ResultAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    private val viewModel: MainViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)



        binding.loadingPanel.visibility = View.INVISIBLE
        repoAdapter = ResultAdapter(viewModel.repoList)
        recyclerView = binding.viewResult
        recyclerView.adapter = repoAdapter
        val etSearchKeyword = binding.etKeyword

        binding.search.setOnClickListener {
            viewModel.repoList.clear()
            repoAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            viewModel.query = etSearchKeyword.text.toString()
            viewModel.pageNumber = 1
            getRepositories()
        }
        setRecyclerViewScrollListener()
        response = Response()
        return binding.root
    }


    fun getRepositories() {
        binding.loadingPanel.visibility = View.VISIBLE
        val response = viewModel.getResponse()
        response.observe(viewLifecycleOwner) { response ->
            response.items.let {
                for (item in it) {
                    viewModel.repoList.add(item)
                }
            }
            repoAdapter = ResultAdapter(viewModel.repoList)
            recyclerView.layoutManager = layoutManager
            repoAdapter.notifyDataSetChanged()

            if (repoAdapter.itemCount == 0) {
                Toast.makeText(
                    context,
                    "no repositories found for the entered keyword",
                    Toast.LENGTH_SHORT
                ).show()
                binding.loadingPanel.visibility = View.GONE
            }
        }
        binding.etKeyword.text.clear()

    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.pageNumber++
                    binding.loadingPanel.visibility = View.VISIBLE
                    getRepositories()
                    val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                    recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }
}