package at.vinatzer.githubsearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.fragment.app.viewModels


class MainFragment : Fragment(R.layout.fragment_main) {

   private val viewModel: MainViewModel by viewModels()



   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      val button = view.findViewById<Button>(R.id.button)
      button.setOnClickListener {
         viewModel.requestResponse()
      }
   }

}