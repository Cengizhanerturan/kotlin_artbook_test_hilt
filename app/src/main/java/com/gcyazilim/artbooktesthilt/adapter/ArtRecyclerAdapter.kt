package com.gcyazilim.artbooktesthilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gcyazilim.artbooktesthilt.databinding.ArtRowBinding
import com.gcyazilim.artbooktesthilt.roomdb.Art
import javax.inject.Inject

class ArtRecyclerAdapter
@Inject
constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {
    class ArtViewHolder(val binding: ArtRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val binding = ArtRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val art = arts[position]
        holder.binding.apply {
            artRowNameText.text = "Name: ${art.name}"
            artRowArtistNameText.text = "Artist Name: ${art.artistName}"
            artRowYearText.text = "Year: ${art.year}"
            glide.load(art.imageUrl).into(artRowImageView)
        }
    }
}