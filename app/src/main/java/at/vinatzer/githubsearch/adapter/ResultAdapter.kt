package at.vinatzer.githubsearch.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import at.vinatzer.githubsearch.MainFragmentDirections
import at.vinatzer.githubsearch.R
import at.vinatzer.githubsearch.model.Item


class ResultAdapter(private val itemList: MutableList<Item>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindLayout(item: Item) {
            val title = itemView.findViewById<TextView>(R.id.title)
            val owner = itemView.findViewById<TextView>(R.id.owner)
            val button = itemView.findViewById<Button>(R.id.InfoButton)
            title.text = item.name
            owner.text = ("by " + item.owner.login)
            itemView.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(item.html_url)
                itemView.context.startActivity(openURL)
            }
            button.setOnClickListener {
                itemView.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(
                        item.name,
                        item.description,
                        item.owner.login,
                        item.html_url
                    )
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindLayout(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}