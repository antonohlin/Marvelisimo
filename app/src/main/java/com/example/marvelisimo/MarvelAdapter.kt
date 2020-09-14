package com.example.marvelisimo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.marvel_item.view.*

class MarvelAdapter(private val marvelList: List<MarvelItem>) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.marvel_item,
            parent, false
        )
        return MarvelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        val currentItem = marvelList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
    }

    override fun getItemCount(): Int {
        return marvelList.size
    }

    class MarvelViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2
    }
}