package com.pmacademy.razvii_project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameListRecyclerViewAdapter(private val gameList: List<Pair<String, Int>>) :
    RecyclerView.Adapter<GameListRecyclerViewAdapter.GameListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_list_winner_item, parent, false)
        return GameListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val winnerName = this.gameList[position].first
        val winnerScore = this.gameList[position].second
        holder.bindItem(winnerName, winnerScore)
    }

    override fun getItemCount(): Int {
        return this.gameList.size
    }

    class GameListViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        private var tvWinnerItem: TextView? = null

        fun bindItem(winnerName: String, winnerScore: Int) {
            tvWinnerItem?.text =
                itemView.context.getString(R.string.rv_text_list_winners, winnerName, winnerScore)
        }

        init {
            tvWinnerItem = itemVIew.findViewById(R.id.tv_winner)
        }

    }
}