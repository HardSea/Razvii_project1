package com.pmacademy.razvii_project1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.razvii_project1.databinding.ActivityWinnerListBinding
import java.lang.Exception

class WinnerListActivity : AppCompatActivity() {

    companion object {

        private lateinit var recyclerViewAdapter: GameListRecyclerViewAdapter
        private var onWinnerListScreen = false

        fun start(context: Context) {
            val intent = Intent(context, WinnerListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }

        fun updateRV(context: Context) {
            if (onWinnerListScreen) {
                if (WinnerList.getSizeList() == 1) {
                    start(context)
                } else {
                    try {
                        recyclerViewAdapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }

    }

    private lateinit var bindings: ActivityWinnerListBinding
    private lateinit var gameList: ArrayList<String>

    private lateinit var recyclerViewGameList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityWinnerListBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        setupActionBar()
        gameList = WinnerList.getListWinners()
        setupRecyclerView()

    }

    override fun onPause() {
        super.onPause()
        onWinnerListScreen = false
        Log.d("mainss", "$onWinnerListScreen onPause")
    }

    override fun onResume() {
        super.onResume()
        onWinnerListScreen = true
        Log.d("mainss", "$onWinnerListScreen onResume")
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar

        actionBar?.title = "List of winners"
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down)
    }

    private fun askUserClearList() {
        val builder = AlertDialog.Builder(this@WinnerListActivity)
        builder.setTitle("Are you sure?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                clearWinnerList()
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }.show()
    }

    private fun clearWinnerList() {
        if (WinnerList.getSizeList() > 0) {
            WinnerList.clearList()
            recyclerViewAdapter.notifyDataSetChanged()
            bindings.rvListWinner.visibility = View.GONE
            bindings.tvEmptyListInfo.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        if (gameList.isEmpty()) {
            bindings.rvListWinner.visibility = View.GONE
            bindings.tvEmptyListInfo.visibility = View.VISIBLE
        } else {
            bindings.tvEmptyListInfo.visibility = View.GONE
            bindings.rvListWinner.visibility = View.VISIBLE
            recyclerViewGameList = bindings.rvListWinner
            recyclerViewGameList.layoutManager = LinearLayoutManager(this)
            recyclerViewAdapter = GameListRecyclerViewAdapter(gameList)
            recyclerViewGameList.adapter = recyclerViewAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_winner_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.btn_clear_list -> {
            if (WinnerList.getSizeList() > 0) askUserClearList()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}