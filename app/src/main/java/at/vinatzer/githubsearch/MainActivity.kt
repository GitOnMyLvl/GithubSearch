package at.vinatzer.githubsearch

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import at.vinatzer.githubsearch.web.createWebService
import at.vinatzer.githubsearch.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    response = Response(createWebService())
    }

    lateinit var searchView: SearchView

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setQueryHint("Search")
        searchView.setSubmitButtonEnabled(true)
        searchView.onActionViewExpanded()
        search(searchView)
        return true
    }

    private fun search(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var query = query
                query = query.lowercase()
                viewModel.search = query
                viewModel.requestResponse()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
    }

}