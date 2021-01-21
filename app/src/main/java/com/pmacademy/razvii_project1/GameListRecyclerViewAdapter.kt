package com.pmacademy.razvii_project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameListRecyclerViewAdapter(private val gameList: ArrayList<String>) :
    RecyclerView.Adapter<GameListRecyclerViewAdapter.GameListViewHolder>() {


    private lateinit var winnerPairList: List<Pair<String, Int>>

    private fun createPairListFromArrayList() {
        winnerPairList = gameList.groupingBy { it }.eachCount().toList().sortedByDescending{ it.second }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {

        createPairListFromArrayList()
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_list_winner_item, parent, false)
        return GameListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val winnerName = winnerPairList[position].first
        val winnerScore = winnerPairList[position].second
        holder.tvGame?.text = holder.itemView.context.getString(R.string.rv_text_list_winners, winnerName, winnerScore)
    }

    override fun getItemCount(): Int {
        return gameList.groupingBy { it }.eachCount().toList().size
    }

    class GameListViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        var tvGame: TextView? = null

        init {
            tvGame = itemVIew.findViewById(R.id.tv_winner)
        }

    }
}