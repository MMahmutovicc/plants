package com.example.spirala

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MedicalListAdapter(
    private var plants:MutableList<Biljka>,
    private var allPlants:MutableList<Biljka>,
    private var images: MutableList<Bitmap>,
    private var allImages: MutableList<Bitmap>,
) : RecyclerView.Adapter<MedicalListAdapter.MedicalViewHolder>() {
    override fun getItemCount(): Int = plants.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medical, parent, false)
        return MedicalViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicalViewHolder, position: Int) {
        holder.nazivItem.text = plants[position].naziv;
        holder.upozorenjeItem.text = plants[position].medicinskoUpozorenje;
        if (plants[position].medicinskeKoristi.size > 2) {
            holder.korist1item.text = plants[position].medicinskeKoristi[0].opis
            holder.korist2item.text = plants[position].medicinskeKoristi[1].opis
            holder.korist3item.text = plants[position].medicinskeKoristi[2].opis
        }
        else if (plants[position].medicinskeKoristi.size > 1) {
            holder.korist1item.text = plants[position].medicinskeKoristi[0].opis
            holder.korist2item.text = plants[position].medicinskeKoristi[1].opis
            holder.korist3item.text = ""
        }
        else if (plants[position].medicinskeKoristi.isNotEmpty()) {
            holder.korist1item.text = plants[position].medicinskeKoristi[0].opis
            holder.korist2item.text = ""
            holder.korist3item.text = ""
        }
        else {
            holder.korist1item.text = ""
            holder.korist2item.text = ""
            holder.korist3item.text = ""
        }
        if(images.size == plants.size)
            holder.slikaItem.setImageBitmap(images[position])
        //println("bitmap=${images[position]}")
    }
    fun updatePlants(plants: MutableList<Biljka>, images: MutableList<Bitmap>) {
        this.plants = plants
        this.images = images
        notifyDataSetChanged()
    }
    fun getPlants(): MutableList<Biljka> {
        return plants
    }
    fun getImages(): MutableList<Bitmap> {
        return images
    }
    fun setAllPlants(plants: MutableList<Biljka>, images: MutableList<Bitmap>) {
        this.allPlants = plants
        this.allImages = images
    }

    inner class MedicalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val upozorenjeItem: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val korist1item: TextView = itemView.findViewById(R.id.korist1Item)
        val korist2item: TextView = itemView.findViewById(R.id.korist2Item)
        val korist3item: TextView = itemView.findViewById(R.id.korist3Item)
        val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        init {
            itemView.setOnClickListener {
                v ->
                var hasAllImages = allPlants.size == allImages.size
                var newPlants = mutableListOf<Biljka>()
                var newImages = mutableListOf<Bitmap>()
                for ((index, biljka) in allPlants.withIndex()) {
                        if(biljka.medicinskeKoristi.any {it in plants[adapterPosition].medicinskeKoristi}) {
                            newPlants.add(biljka)
                            if(hasAllImages)
                                newImages.add(allImages[index])
                        }
                }
                updatePlants(newPlants, newImages)
            }
        }
    }
}