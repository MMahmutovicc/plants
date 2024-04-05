package com.example.spirala

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MedicalListAdapter(
    private var plants:List<Biljka>
) : RecyclerView.Adapter<MedicalListAdapter.MedicalViewHolder>() {
    override fun getItemCount(): Int = plants.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medical, parent, false)
        return MedicalViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicalViewHolder, position: Int) {
        holder.nazivItem.text = plants[position].naziv;
        holder.upozorenjeItem.text = plants[position].medicinskoUpozorenje;
        holder.korist1item.text = plants[position].medicinskeKoristi[0].opis;
        if(plants[position].medicinskeKoristi.size > 2) {
            holder.korist2item.text = plants[position].medicinskeKoristi[1].opis;
            holder.korist3item.text = plants[position].medicinskeKoristi[2].opis;
        }
        else if(plants[position].medicinskeKoristi.size > 1) {
            holder.korist2item.text = plants[position].medicinskeKoristi[1].opis;
            holder.korist3item.text = "";
        }
        else {
            holder.korist2item.text = "";
            holder.korist3item.text = "";
        }
    }
    fun updatePlants(plants: List<Biljka>) {
        this.plants = plants
        notifyDataSetChanged()
    }
    fun getPlants(): List<Biljka> {
        return plants
    }

    inner class MedicalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val upozorenjeItem: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val korist1item: TextView = itemView.findViewById(R.id.korist1Item)
        val korist2item: TextView = itemView.findViewById(R.id.korist2Item)
        val korist3item: TextView = itemView.findViewById(R.id.korist3Item)
        init {
            itemView.setOnClickListener {
                v ->
                var biljke = getPLants()
                var newPlants = mutableListOf<Biljka>()
                for (biljka in biljke) {
                        if(biljka.medicinskeKoristi.any {it in plants[adapterPosition].medicinskeKoristi}) {
                            newPlants.add(biljka)
                        }
                }
                updatePlants(newPlants)
            }
        }
    }
}