package at.vinatzer.githubsearch.application

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.vinatzer.githubsearch.Response
import at.vinatzer.githubsearch.adapter.ResultAdapter
import at.vinatzer.githubsearch.model.Item
import at.vinatzer.githubsearch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repoList: MutableList<Item>
    private lateinit var repoAdapter: ResultAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    var pageNumber: Int = 1
    lateinit var queryKeyword: String

    @SuppressLint("CommitPrefEdits", "NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repoList = mutableListOf()

        binding.loadingPanel.visibility = View.INVISIBLE
        repoAdapter = ResultAdapter(repoList)
        recyclerView = binding.viewResult
        recyclerView.adapter = repoAdapter
        val etSearchKeyword = binding.etKeyword

        binding.search.setOnClickListener { repoList.clear()
            repoAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            queryKeyword = etSearchKeyword.text.toString()

            val prefs = getSharedPreferences("Share", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("queryKw", queryKeyword)
            editor.putInt("pageNr", pageNumber)

            getRepositories()
        }
        setRecyclerViewScrollListener()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun getRepositories() {
        binding.loadingPanel.visibility = View.VISIBLE
        val prefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
        val prefQueryKeyword = prefs.getString("queryKw", queryKeyword)
        val prefPageNumber = prefs.getInt("pageNr", pageNumber)

        val response = Response().getResponse(prefQueryKeyword.toString(), prefPageNumber, 20)
        response.observe(this) { response ->
            response.items.let {
                for (item in it) {
                    repoList.add(item)
                }
            }
            repoAdapter = ResultAdapter(repoList)
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
                    val prefs = getSharedPreferences("Share", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = prefs.edit()
                    val oldPageNumber = prefs.getInt("pageNr", pageNumber)
                    pageNumber = oldPageNumber + 1
                    editor.putInt("pageNr", pageNumber)
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