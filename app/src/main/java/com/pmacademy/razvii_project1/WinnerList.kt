package com.pmacademy.razvii_project1

class WinnerList {
    companion object {
        private val winnerList: ArrayList<String> = arrayListOf()

        val size: Int
            get() = winnerList.size

        fun clearList() {
            winnerList.clear()
        }

        fun addWinner(teamName: String) {
            winnerList.add(teamName)
        }

        //return sorted list of winner and number winning games
        fun getListPairWinners(): List<Pair<String, Int>> {
            return winnerList.groupingBy { it }.eachCount().toList()
                .sortedByDescending { it.second }
        }
    }
}