package at.vinatzer.githubsearch

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.vinatzer.githubsearch.adapter.ResultAdapter
import at.vinatzer.githubsearch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repoAdapter: ResultAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    private val viewModel: MainViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loadingPanel.visibility = View.INVISIBLE
        repoAdapter = ResultAdapter(viewModel.repoList)
        recyclerView = binding.viewResult
        recyclerView.adapter = repoAdapter
        val etSearchKeyword = binding.etKeyword

        binding.search.setOnClickListener {
            viewModel.repoList.clear()
            repoAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            viewModel.query = etSearchKeyword.text.toString()
            viewModel.pageNumber = 1
            getRepositories()
        }
        setRecyclerViewScrollListener()

        response = Response()
    }


    fun getRepositories() {
        binding.loadingPanel.visibility = View.VISIBLE
        val response = viewModel.getResponse()
        response.observe(this) { response ->
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
                    this,
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