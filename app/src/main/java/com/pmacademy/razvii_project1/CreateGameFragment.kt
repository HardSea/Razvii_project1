package com.pmacademy.razvii_project1

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pmacademy.razvii_project1.databinding.FragmentCreatingGameBinding


class CreateGameFragment : Fragment() {


    private lateinit var binding: FragmentCreatingGameBinding

    private var hoursGameTime: Int = 0
    private var minutesGameTime: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatingGameBinding.inflate(layoutInflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnSetupTime.setOnClickListener {
            startTimePickerDialog()
        }
        binding.tvGameTime.setOnClickListener {
            startTimePickerDialog()
        }
        binding.btnStartGame.setOnClickListener {
            startGameFragment()
        }
    }

    private fun startGameFragment() {
        val firstTeamName = binding.editNameFirstTeam.text.toString()
        val secondTeamName = binding.editNameSecondTeam.text.toString()

        val creatingGameFragment = CurrentGameFragment.newInstance(
            firstTeamName,
            secondTeamName,
            hoursGameTime,
            minutesGameTime
        )

        fragmentManager?.beginTransaction()?.replace(R.id.root_container, creatingGameFragment)
            ?.commitAllowingStateLoss()

    }


    private fun startTimePickerDialog() {

        val onTimeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                hoursGameTime = hour
                minutesGameTime = minute
                showTvWithTime()
            }

        TimePickerDialog(
            context,
            R.style.TimePickerTheme,
            onTimeSetListener,
            0,
            15,
            true
        ).show()
    }

    private fun showTvWithTime() {

        val strBuf = StringBuffer()
        strBuf.append("Game time: ")

        if (hoursGameTime >= 3) {
            Toast.makeText(
                context,
                "Warning! You have chosen too much time",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (hoursGameTime >= 1) {
            strBuf.append(hoursGameTime)
            strBuf.append(" hour and ")
            strBuf.append(minutesGameTime)
            strBuf.append(" minutes or ")
            strBuf.append(timeToMinute(hoursGameTime, minutesGameTime))
            strBuf.append(" minutes")
        } else {
            strBuf.append(minutesGameTime)
            strBuf.append(" minutes")
        }
        binding.tvGameTime.text = strBuf.toString()
        binding.tvGameTime.visibility = View.VISIBLE
        binding.btnSetupTime.visibility = View.GONE

    }

    private fun timeToMinute(hour: Int, minute: Int): Int {
        return hour * 60 + minute
    }


    companion object {
        fun newInstance() = CreateGameFragment()
    }
}