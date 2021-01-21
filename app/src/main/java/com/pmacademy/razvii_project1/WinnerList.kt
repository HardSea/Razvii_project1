package com.pmacademy.razvii_project1

class WinnerList {
    companion object {
        private val winnerList: ArrayList<String> = arrayListOf()

        fun clearList() {
            winnerList.clear()
        }

        fun addWinner(teamName: String) {
            winnerList.add(teamName)
        }

        fun getSizeList(): Int {
            return winnerList.size
        }

        fun getListWinners(): ArrayList<String> {
            return winnerList
        }
    }
}