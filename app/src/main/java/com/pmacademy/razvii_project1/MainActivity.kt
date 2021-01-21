package com.pmacademy.razvii_project1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val creatingGameFragment = CreateGameFragment.newInstance()

        supportFragmentManager.beginTransaction().replace(R.id.root_container, creatingGameFragment)
            .commitAllowingStateLoss()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main_menu, menu)
        return true
    }

    private fun startWinnerListActivity() {
        //TODO("input game list array")
        WinnerListActivity.start(this)
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.show_winner_list -> {
            startWinnerListActivity()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}

