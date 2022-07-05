package at.vinatzer.githubsearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.fragment.app.viewModels
import android.widget.SearchView
import android.view.MenuInflater
import androidx.recyclerview.widget.LinearLayoutManager
import at.vinatzer.githubsearch.adapter.ResultAdapter
import at.vinatzer.githubsearch.model.Item
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

   private val viewModel: MainViewModel by viewModels()




   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      val button = view.findViewById<Button>(R.id.button)
      button.setOnClickListener {
        refreshResultInList(viewModel.getArrayList())
      }



   }

   fun refreshResultInList(result: ArrayList<Item>) {
      val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      val adapter = ResultAdapter(result)
      recyclerView.layoutManager = linearLayoutManager
      recyclerView.adapter = adapter
   }



}