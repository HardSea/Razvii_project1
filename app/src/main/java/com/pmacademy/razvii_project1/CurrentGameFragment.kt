package com.pmacademy.razvii_project1

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import com.pmacademy.razvii_project1.databinding.FragmentCurrentGameBinding
import java.lang.StringBuilder
import java.util.*

private const val ARG_PARAM_TEAM_NAME_FIRST = "com.pmacademy.razvii_project1.firstTeamName"
private const val ARG_PARAM_TEAM_NAME_SECOND = "com.pmacademy.razvii_project1.secondTeamName"
private const val ARG_PARAM_HOURS_GAME = "com.pmacademy.razvii_project1.hoursGameTime"
private const val ARG_PARAM_MINUTES_GAME = "com.pmacademy.razvii_project1.minutesGameTime"


class CurrentGameFragment : Fragment() {

    private var firstTeamName: String? = null
    private var secondTeamName: String? = null
    private var hoursGameTime: Int? = null
    private var minutesGameTime: Int? = null

    private var totalPlayedTime: Int = 0

    private var scoreFirstTeam = 0
    private var scoreSecondTeam = 0

    private lateinit var countDownTimer: CountDownTimer

    private lateinit var binding: FragmentCurrentGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstTeamName = it.getString(ARG_PARAM_TEAM_NAME_FIRST)
            secondTeamName = it.getString(ARG_PARAM_TEAM_NAME_SECOND)
            hoursGameTime = it.getInt(ARG_PARAM_HOURS_GAME)
            minutesGameTime = it.getInt(ARG_PARAM_MINUTES_GAME)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentGameBinding.inflate(layoutInflater, container, false)
        setupListeners()
        binding.tvNameFirstTeam.text = firstTeamName
        binding.tvNameSecondTeam.text = secondTeamName
        startTimer()
        return binding.root
    }

    private fun startTimer() {
        if (hoursGameTime != null && minutesGameTime != null) {
            val timeInMillis =
                ((hoursGameTime!! * 60 * 60 * 1000) + (minutesGameTime!! * 60 * 1000)).toLong()
            countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    updateCountDownTimer(millisUntilFinished)
                    totalPlayedTime++
                }

                override fun onFinish() {
                    showGameResult()
                    Toast.makeText(context, "Time finished", Toast.LENGTH_SHORT).show()
                }
            }
            countDownTimer.start()
        } else {
            Toast.makeText(context, "Error when start timer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCountDownTimer(timeLeftInMillis: Long) {
        val hours = (timeLeftInMillis / (1000 * 60 * 60)) % 60
        val minutes = (timeLeftInMillis / (1000 * 60)) % 60
        val seconds = (timeLeftInMillis / 1000) % 60

        val strFormatted =
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)

        binding.tvTimeLeft.text = strFormatted
    }

    private fun changeGameScore(score: Int, team: TeamSequentialNumber) {
        val tempStrForTvGameScore = StringBuilder()
        when (team) {
            TeamSequentialNumber.FIRST_TEAM -> {
                if (scoreFirstTeam == 0 && score == -1) return
                scoreFirstTeam += score
                if (scoreFirstTeam < 10) {
                    tempStrForTvGameScore.append("0")
                }
                tempStrForTvGameScore.append(scoreFirstTeam.toString())
                binding.tvGameScoreFirstTeam.text = tempStrForTvGameScore
            }
            TeamSequentialNumber.SECOND_TEAM -> {
                if (scoreSecondTeam == 0 && score == -1) return
                scoreSecondTeam += score
                if (scoreSecondTeam < 10) {
                    tempStrForTvGameScore.append("0")
                }
                tempStrForTvGameScore.append(scoreSecondTeam.toString())
                binding.tvGameScoreSecondTeam.text = tempStrForTvGameScore
            }
        }
    }

    private fun cancelGame() {
        var saveGameResult = false
        val checkBoxView = View.inflate(context, R.layout.alertdialog_chekbox, null)
        val checkBox = checkBoxView.findViewById<View>(R.id.checkbox_alert_dialog) as CheckBox
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            saveGameResult = isChecked
        }

        checkBox.text = getString(R.string.checkbox_text_q)

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Finish this game?")
            .setView(checkBoxView)
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                countDownTimer.cancel()
                if (saveGameResult) {
                    showGameResult()
                } else {
                    startCreateGameFragment()
                }
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }.show()
    }

    private fun startCreateGameFragment() {
        val creatingGameFragment = CreateGameFragment.newInstance()

        fragmentManager?.beginTransaction()?.replace(R.id.root_container, creatingGameFragment)
            ?.commitAllowingStateLoss()
    }

    private fun showGameResult() {
        saveGameResult()
        if (firstTeamName != null && secondTeamName != null) {
            val resultGameFragment = ResultGameFragment.newInstance(
                firstTeamName!!,
                secondTeamName!!,
                scoreFirstTeam,
                scoreSecondTeam,
                totalPlayedTime
            )
            fragmentManager?.beginTransaction()?.replace(R.id.root_container, resultGameFragment)
                ?.commitAllowingStateLoss()
        }
    }

    private fun saveGameResult() {
        // if draw we don't save game result in winner list
        if (scoreFirstTeam != scoreSecondTeam)
            if (scoreFirstTeam > scoreSecondTeam) {
                firstTeamName?.let { WinnerList.addWinner(it) }
            } else {
                secondTeamName?.let { WinnerList.addWinner(it) }
            }
    }

    private fun setupListeners() {
        binding.tvCancelGame.setOnClickListener {
            cancelGame()
        }
        binding.btnAddPointFirstTeam.setOnClickListener {
            changeGameScore(1, TeamSequentialNumber.FIRST_TEAM)
        }
        binding.btnNegPointFirstTeam.setOnClickListener {
            changeGameScore(-1, TeamSequentialNumber.FIRST_TEAM)
        }
        binding.btnAddPointSecondTeam.setOnClickListener {
            changeGameScore(1, TeamSequentialNumber.SECOND_TEAM)
        }
        binding.btnNegPointSecondTeam.setOnClickListener {
            changeGameScore(-1, TeamSequentialNumber.SECOND_TEAM)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            firstTeamName: String,
            secondTeamName: String,
            hoursGameTime: Int,
            minutesGameTime: Int
        ) =
            CurrentGameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_TEAM_NAME_FIRST, firstTeamName)
                    putString(ARG_PARAM_TEAM_NAME_SECOND, secondTeamName)
                    putInt(ARG_PARAM_HOURS_GAME, hoursGameTime)
                    putInt(ARG_PARAM_MINUTES_GAME, minutesGameTime)
                }
            }
    }
}

private enum class TeamSequentialNumber {
    FIRST_TEAM,
    SECOND_TEAM
}