package at.vinatzer.githubsearch.adapter


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import at.vinatzer.githubsearch.R
import at.vinatzer.githubsearch.model.Item




class ResultViewHolder(val listItemResultRootView: View): RecyclerView.ViewHolder(listItemResultRootView) {
    val resultNameTextView: TextView = listItemResultRootView.findViewById(R.id.tvListItemResultName)
}

class ResultAdapter(private var items: ArrayList<Item>): RecyclerView.Adapter<ResultViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemResultRootView =
            layoutInflater.inflate(R.layout.list_item_result, parent, false)
        return ResultViewHolder(listItemResultRootView)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = items[position]
        holder.resultNameTextView.text = item.name
        holder.listItemResultRootView.setOnClickListener{
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse(item.html_url)
            println(item.html_url)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

}