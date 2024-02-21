package com.example.saverx.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.saverx.R
import com.example.saverx.app.model.SourceModel

class LinkAdapter(private val onItemClickListener: (state: Int) -> Unit): RecyclerView.Adapter<LinkAdapter.LinkHolder>() {

    val testdata = listOf(
        SourceModel(R.drawable.ic_vk, "ВКонтакте"),
        SourceModel(R.drawable.ic_launcher_foreground, "Что-то ещё"),
        SourceModel(R.drawable.ic_vk, "Одноклассники"),
        SourceModel(R.drawable.ic_launcher_background, "ВКонтакте")
        )

   inner class LinkHolder(item: View): ViewHolder(item) {
      private val itemImage: ImageView = itemView.findViewById(R.id.item_image)
      private val itemText: TextView = itemView.findViewById(R.id.item_text)

       fun bind(source: SourceModel){
             itemImage.setImageResource(source.imageID)
             itemText.text = source.title
          }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.link_item, parent, false)
        return LinkHolder(view)
    }

    override fun onBindViewHolder(holder: LinkHolder, position: Int) {
        holder.bind(testdata[position])

        holder.itemView.setOnClickListener {
            onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return testdata.size
    }
}