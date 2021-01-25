package com.pmacademy.razvii_project1

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pmacademy.razvii_project1.databinding.FragmentCreatingGameBinding
import java.util.*

class CreateGameFragment : Fragment() {

    private lateinit var binding: FragmentCreatingGameBinding

    private var hoursGameTime: Int = 0
    private var minutesGameTime: Int = 0
    private var timeChosen = false

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
            showTimePickerDialog()
        }
        binding.tvGameTime.setOnClickListener {
            showTimePickerDialog()
        }
        binding.btnStartGame.setOnClickListener {
            startGameFragment()
        }
        binding.editNameFirstTeam.addTextChangedListener(textWatcher)
        binding.editNameSecondTeam.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            checkEditTextsForRules()
        }
    }

    private fun checkEditTextsForRules() {
        val editFirstTeamName = binding.editNameFirstTeam
        val editSecondTeamName = binding.editNameSecondTeam
        val btnStart = binding.btnStartGame

        val textFirstEdit =
            editFirstTeamName.text.toString().trim().replace("\\s+".toRegex(), " ")
                .toLowerCase(Locale.ROOT)
        val textSecondEdit =
            editSecondTeamName.text.toString().trim().replace("\\s+".toRegex(), " ").toLowerCase(
                Locale.ROOT
            )

        val sameText = textFirstEdit != textSecondEdit
        val emptyLines = editFirstTeamName.length() != 0 && editSecondTeamName.length() != 0

        btnStart.isEnabled = timeChosen && emptyLines && sameText
    }

    private fun showTimePickerDialog() {
        val onTimeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                hoursGameTime = hour
                minutesGameTime = minute
                timeChosen = true
                checkEditTextsForRules()
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

    private fun startGameFragment() {
        val firstTeamName =
            binding.editNameFirstTeam.text.toString().trim().replace("\\s+".toRegex(), " ")
        val secondTeamName =
            binding.editNameSecondTeam.text.toString().trim().replace("\\s+".toRegex(), " ")
        val firstTeam = Team(firstTeamName)
        val secondTeam = Team(secondTeamName)
        val creatingGameFragment = CurrentGameFragment.newInstance(
            firstTeam,
            secondTeam,
            hoursGameTime,
            minutesGameTime
        )
        fragmentManager?.beginTransaction()?.replace(R.id.root_container, creatingGameFragment)
            ?.commitAllowingStateLoss()
    }

    private fun timeToMinute(hour: Int, minute: Int): Int {
        return hour * 60 + minute
    }

    companion object {
        fun newInstance() = CreateGameFragment()
    }
}