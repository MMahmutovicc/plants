package com.example.spirala

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CookingListAdapter(
    private var plants:List<Biljka>
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
        holder.jelo1item.text = plants[position].jela[0];
        if(plants[position].jela.size > 2) {
            holder.jelo2item.text = plants[position].jela[1];
            holder.jelo3item.text = plants[position].jela[2];
        }
        else if(plants[position].jela.size > 1) {
            holder.jelo2item.text = plants[position].jela[1];
            holder.jelo3item.text = "";
        }
        else {
            holder.jelo2item.text = "";
            holder.jelo3item.text = "";
        }
    }

    fun updatePlants(plants: List<Biljka>) {
        this.plants = plants
        notifyDataSetChanged()
    }
    fun getPlants(): List<Biljka> {
        return plants
    }
    inner class CookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val profilOkusaItem: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val jelo1item: TextView = itemView.findViewById(R.id.jelo1Item)
        val jelo2item: TextView = itemView.findViewById(R.id.jelo2Item)
        val jelo3item: TextView = itemView.findViewById(R.id.jelo3Item)
        init {
            itemView.setOnClickListener {
                v ->
                var biljke = getPLants()
                var newPlants = mutableListOf<Biljka>()
                for (biljka in biljke) {
                    if (plants[adapterPosition].profilOkusa == biljka.profilOkusa)
                        newPlants.add(biljka)
                    else if (biljka.jela.any {it in plants[adapterPosition].jela}) {
                        newPlants.add(biljka)
                    }
                }
                updatePlants(newPlants)
            }
        }
    }
}