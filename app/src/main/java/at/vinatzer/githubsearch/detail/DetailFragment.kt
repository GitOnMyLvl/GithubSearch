package at.vinatzer.githubsearch.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import at.vinatzer.githubsearch.R
import at.vinatzer.githubsearch.databinding.FragmentDetailBinding


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailBinding.inflate(layoutInflater)



        binding.tvName.text = ("Name: " + viewModel.getRepoName)
        binding.tvOwner.text = ("Owner: " + viewModel.getRepoOwner)
        binding.tvUrl.text = ("Url: " + viewModel.getRepoUrl)

        if(viewModel.getRepoDescription != null) {
            binding.tvDescription.text = ("Description: " + viewModel.getRepoDescription)
        }

        binding.backButton.setOnClickListener(){
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToMainFragment())
        }

        binding.tvUrl.setOnClickListener(){
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(viewModel.getRepoUrl)
            context?.startActivity(openURL)
        }

        return binding.root
    }
}
