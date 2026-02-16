package com.example.tcgvault.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tcgvault.R
import com.example.tcgvault.data.CardEntity

class CardAdapter(private var cards: List<CardEntity>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCardLine: TextView = itemView.findViewById(R.id.txtCardLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val c = cards[position]
        holder.txtCardLine.text = "${c.cardName} • ${c.tcgType} • Qty: ${c.quantity}"
    }

    override fun getItemCount(): Int = cards.size

    fun update(newCards: List<CardEntity>) {
        cards = newCards
        notifyDataSetChanged()
    }
}
