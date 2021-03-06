package com.example.marvelisimo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelisimo.R
import com.example.marvelisimo.model.MarvelItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.marvel_item.view.*

class MarvelAdapter(
    val context: Context,
    private val marvelList: List<MarvelItem>,
    //private val marvelListFull: List<MarvelItem>,
    private val listener:(MarvelItem) -> Unit)
    : RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.marvel_item,
            parent, false
        )
        return MarvelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.bindView(marvelList[position], listener)
    }

    override fun getItemCount(): Int {
        return marvelList.size
    }

    class MarvelViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val characterThumbnail: ImageView = itemView.character_thumbnail
        val characterThumbnail2: ImageView = itemView.character_thumbnail2
        val characterTitle: TextView = itemView.character_title

        fun bindView(item: MarvelItem, listener: (MarvelItem) -> Unit) {
            Picasso.get().load(item.imageUrl).into(characterThumbnail)
            Picasso.get().load(item.imageUrl).into(characterThumbnail2)
            characterTitle.text = item.title
            itemView.setOnClickListener { listener(item) }
        }


    }
}