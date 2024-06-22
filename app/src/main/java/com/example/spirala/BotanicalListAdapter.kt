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
    private var images: MutableMap<Long,Bitmap>,
    private var flowerColorImages: MutableList<Bitmap> = mutableListOf(),
    private var flowerColor : Int = 0
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
        if (flowerColor == 0 && images.containsKey(plants[position].id))
            holder.slikaItem.setImageBitmap(images[plants[position].id])
        else if (flowerColor == 1 && flowerColorImages.size == plants.size)
            holder.slikaItem.setImageBitmap(flowerColorImages[position])
    }

    fun updatePlants(plants: MutableList<Biljka>, flowerColor: Int = 0, images: MutableList<Bitmap> = this.flowerColorImages) {
        this.flowerColor = flowerColor
        this.plants = plants
        this.flowerColorImages = images
        notifyDataSetChanged()
    }
    fun getPlants(): MutableList<Biljka> {
        if (flowerColor == 1) {
            flowerColor = 0
            return allPlants
        }
        return plants
    }

    fun updateAll(plants: MutableList<Biljka>, images: MutableMap<Long,Bitmap>) {
        this.plants - plants
        this.allPlants = plants
        this.images = images
        notifyDataSetChanged()
    }

    /*fun getImages(): MutableList<Bitmap> {
        if (flowerColor == 1) {
            flowerColor = 0
            return allImages
        }
        return images
    }*/

    inner class BotanicalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val porodicaItem: TextView = itemView.findViewById(R.id.porodicaItem)
        val klimatskiTipItem: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljisniTipItem: TextView = itemView.findViewById(R.id.zemljisniTipItem)
        val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        init {
            itemView.setOnClickListener {
                    v ->
                if (flowerColor == 0) {
                    var newPlants = mutableListOf<Biljka>()
                    for (plant in allPlants) {
                        if (plants[adapterPosition].porodica == plant.porodica) {
                            if (plant.klimatskiTipovi.any {it in plants[adapterPosition].klimatskiTipovi} &&
                                plant.zemljisniTipovi.any {it in plants[adapterPosition].zemljisniTipovi}) {
                                newPlants.add(plant)
                            }
                        }
                    }
                    updatePlants(newPlants)
                }
            }
        }
    }
}