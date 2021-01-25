package com.pmacademy.razvii_project1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val creatingGameFragment = CreateGameFragment.newInstance()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportFragmentManager.beginTransaction().replace(R.id.root_container, creatingGameFragment)
            .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main_menu, menu)
        return true
    }

    private fun startWinnerListActivity() {
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