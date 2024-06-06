package com.example.spirala

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class BotanicalListAdapter(
    private var plants:MutableList<Biljka>,
    private var allPlants: MutableList<Biljka>,
    private var images: MutableList<Bitmap>,
    private var allImages: MutableList<Bitmap>,
    private var flower_color : Int = 0
) : RecyclerView.Adapter<BotanicalListAdapter.BotanicalViewHolder>() {
    override fun getItemCount(): Int = plants.size
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BotanicalViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_botanical, parent, false)
        return BotanicalViewHolder(view)
    }

    override fun onBindViewHolder(holder: BotanicalViewHolder, position: Int) {
        holder.nazivItem.text = plants[position].naziv;
        holder.porodicaItem.text = plants[position].porodica;
        if (plants[position].klimatskiTipovi.isNotEmpty())
            holder.klimatskiTipItem.text = plants[position].klimatskiTipovi[0].opis;
        else
            holder.klimatskiTipItem.text = "";
        if (plants[position].zemljisniTipovi.isNotEmpty())
            holder.zemljisniTipItem.text = plants[position].zemljisniTipovi[0].naziv;
        else
            holder.zemljisniTipItem.text = "";
        if(images.size == plants.size)
            holder.slikaItem.setImageBitmap(images[position])
    }

    fun updatePlants(plants: MutableList<Biljka>, images: MutableList<Bitmap>, flower_color: Int = 0) {
        this.flower_color = flower_color
        this.plants = plants
        this.images = images
        notifyDataSetChanged()
    }
    fun getPlants(): MutableList<Biljka> {
        if (flower_color == 1) {
            flower_color = 0
            return allPlants
        }
        return plants
    }

    fun setAllPlants(plants: MutableList<Biljka>, images: MutableList<Bitmap>) {
        this.allPlants = plants
        this.allImages = images
    }

    fun getImages(): MutableList<Bitmap> {
        if (flower_color == 1) {
            flower_color = 0
            return allImages
        }
        return images
    }

    inner class BotanicalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val porodicaItem: TextView = itemView.findViewById(R.id.porodicaItem)
        val klimatskiTipItem: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljisniTipItem: TextView = itemView.findViewById(R.id.zemljisniTipItem)
        val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        init {
            itemView.setOnClickListener {
                    v ->
                if (flower_color == 0) {
                    val hasAllImages = allPlants.size == allImages.size
                    var newPlants = mutableListOf<Biljka>()
                    var newImages = mutableListOf<Bitmap>()
                    for ((index, biljka) in allPlants.withIndex()) {
                        if (plants[adapterPosition].porodica == biljka.porodica) {
                            if (biljka.klimatskiTipovi.any {it in plants[adapterPosition].klimatskiTipovi} &&
                                biljka.zemljisniTipovi.any {it in plants[adapterPosition].zemljisniTipovi}) {
                                newPlants.add(biljka)
                                if (hasAllImages)
                                    newImages.add(allImages[index])
                            }
                        }
                    }
                    updatePlants(newPlants, newImages)
                }
            }
        }
    }
}