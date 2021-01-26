package com.pmacademy.razvii_project1

class WinnerList {
    companion object {
        private val winnerList: ArrayList<Pair<String, Int>> = arrayListOf()

        val size: Int
            get() = winnerList.size

        fun clearList() {
            winnerList.clear()
        }

        fun addWinner(teamName: String, scoreCnt: Int) {
            winnerList.add(Pair(teamName, scoreCnt))
        }

        //return sorted list of winner and number winning games
        fun getDescendingListPairWinners(): List<Pair<String, Int>> {

            val winnersMap = winnerList.groupBy({ it.first }, { it.second })

            val returnList: ArrayList<Pair<String, Int>> = arrayListOf()
            val iteratorWinners = winnersMap.iterator()

            for (winner in iteratorWinners) {
                val sumScore = winner.value.sum()
                returnList.add(Pair(winner.key, sumScore))
            }

            return returnList.sortedByDescending { it.second }
        }
    }
}