package com.withapp.coffeememo.copyright.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.copyright.presentation.model.CopyRightInfo

class CopyRightAdapter(
    private val data: List<CopyRightInfo>,
    private val onUrlClick: (urlStr: String) -> Unit
) : RecyclerView.Adapter<CopyRightAdapter.CopyRightItemViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CopyRightItemViewHolder {
        return CopyRightItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.copy_right_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(
        holder: CopyRightItemViewHolder,
        position: Int
    ) {
        holder.author.text = data[position].author
        holder.url.text = data[position].url
        holder.img.setImageResource(data[position].imageId)

        holder.url.setOnClickListener {
            onUrlClick(data[position].url)
        }
    }

    // ViewHolder
    class CopyRightItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.findViewById(R.id.authorValue)
        val url: TextView = itemView.findViewById(R.id.urlValue)
        val img: ImageView = itemView.findViewById(R.id.img)
    }
}
