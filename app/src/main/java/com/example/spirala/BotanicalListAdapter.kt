package com.example.spirala

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class BotanicalListAdapter(
    private var plants:List<Biljka>
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
        holder.klimatskiTipItem.text = plants[position].klimatskiTipovi[0].opis;
        holder.zemljisniTipItem.text = plants[position].zemljisniTipovi[0].naziv;
    }

    fun updatePlants(plants: List<Biljka>) {
        this.plants = plants
        notifyDataSetChanged()
    }
    fun getPlants(): List<Biljka> {
        return plants
    }
    inner class BotanicalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val porodicaItem: TextView = itemView.findViewById(R.id.porodicaItem)
        val klimatskiTipItem: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljisniTipItem: TextView = itemView.findViewById(R.id.zemljisniTipItem)
        init {
            itemView.setOnClickListener {
                    v ->
                println(adapterPosition)
                var biljke = getPLants()
                var newPlants = mutableListOf<Biljka>()
                for (biljka in biljke) {
                    if (plants[adapterPosition].porodica == biljka.porodica) {
                        if (biljka.klimatskiTipovi.any {it in plants[adapterPosition].klimatskiTipovi} &&
                            biljka.zemljisniTipovi.any {it in plants[adapterPosition].zemljisniTipovi})
                            newPlants.add(biljka)
                    }
                }
                updatePlants(newPlants)
            }
        }
    }
}