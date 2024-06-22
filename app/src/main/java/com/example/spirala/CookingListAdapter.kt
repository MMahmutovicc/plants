package com.example.spirala

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CookingListAdapter(
    private var plants:MutableList<Biljka>,
    private var allPlants:MutableList<Biljka>,
    private var images: MutableMap<Long,Bitmap>
) : RecyclerView.Adapter<CookingListAdapter.CookingViewHolder>() {
    override fun getItemCount(): Int = plants.size
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CookingViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cooking, parent, false)
        return CookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: CookingViewHolder, position: Int) {
        holder.nazivItem.text = plants[position].naziv;
        holder.profilOkusaItem.text = plants[position].profilOkusa.opis;
        if (plants[position].jela.size > 2) {
            holder.jelo1item.text = plants[position].jela[0]
            holder.jelo2item.text = plants[position].jela[1]
            holder.jelo3item.text = plants[position].jela[2]
        }
        else if (plants[position].jela.size > 1) {
            holder.jelo1item.text = plants[position].jela[0]
            holder.jelo2item.text = plants[position].jela[1]
            holder.jelo3item.text = ""
        }
        else if (plants[position].jela.isNotEmpty()) {
            holder.jelo1item.text = plants[position].jela[0]
            holder.jelo2item.text = ""
            holder.jelo3item.text = ""
        }
        else {
            holder.jelo1item.text = ""
            holder.jelo2item.text = ""
            holder.jelo3item.text = ""
        }
        if(images.containsKey(plants[position].id))
            holder.slikaItem.setImageBitmap(images[plants[position].id])
    }

    fun updatePlants(plants: MutableList<Biljka>) {
        this.plants = plants
        notifyDataSetChanged()
    }
    fun getPlants(): MutableList<Biljka> {
        return plants
    }

    /*fun getImages(): MutableMap<Long,Bitmap> {
        return images
    }*/

    fun updateAll(plants: MutableList<Biljka>, images: MutableMap<Long,Bitmap>) {
        this.allPlants = plants
        this.plants = plants
        this.images = images
        notifyDataSetChanged()
    }

    inner class CookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val profilOkusaItem: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val jelo1item: TextView = itemView.findViewById(R.id.jelo1Item)
        val jelo2item: TextView = itemView.findViewById(R.id.jelo2Item)
        val jelo3item: TextView = itemView.findViewById(R.id.jelo3Item)
        val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        init {
            itemView.setOnClickListener {
                v ->
                var newPlants = mutableListOf<Biljka>()
                for (plant in allPlants) {
                    if (plants[adapterPosition].profilOkusa == plant.profilOkusa) {
                        newPlants.add(plant)

                    }
                    else if (plant.jela.any {it in plants[adapterPosition].jela}) {
                        newPlants.add(plant)

                    }
                }
                updatePlants(newPlants)
            }
        }
    }
}