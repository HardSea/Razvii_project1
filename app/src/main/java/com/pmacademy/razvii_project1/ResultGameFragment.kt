package com.pmacademy.razvii_project1

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pmacademy.razvii_project1.databinding.FragmentResultGameBinding
import java.util.*


private const val ARG_PARAM_TEAM_FIRST = "com.pmacademy.razvii_project1.firstTeam"
private const val ARG_PARAM_TEAM_SECOND = "com.pmacademy.razvii_project1.secondTeam"
private const val ARG_PARAM_TEAM_SCORE_FIRST = "com.pmacademy.razvii_project1.firstTeamScore"
private const val ARG_PARAM_TEAM_SCORE_SECOND = "com.pmacademy.razvii_project1.secondTeamScore"
private const val ARG_PARAM_TOTAL_GAME_TIME = "com.pmacademy.razvii_project1.totalGameTime"


class ResultGameFragment : Fragment() {
    private var firstTeam: Team? = null
    private var secondTeam: Team? = null
    private var firstTeamScore: Int? = null
    private var secondTeamScore: Int? = null
    private var totalGameTime: Int? = null

    private var winnerTeamString: String = ""


    private lateinit var binding: FragmentResultGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstTeam = it.getParcelable(ARG_PARAM_TEAM_FIRST)
            secondTeam = it.getParcelable(ARG_PARAM_TEAM_SECOND)
            firstTeamScore = it.getInt(ARG_PARAM_TEAM_SCORE_FIRST)
            secondTeamScore = it.getInt(ARG_PARAM_TEAM_SCORE_SECOND)
            totalGameTime = it.getInt(ARG_PARAM_TOTAL_GAME_TIME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultGameBinding.inflate(layoutInflater, container, false)
        printResult()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnGoToMainScreen.setOnClickListener {
            startCreateGameFragment()
        }
        binding.btnShareResult.setOnClickListener {
            shareResults()
        }
    }

    private fun shareResults() {

        val shareString = getResultText()
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            shareString
        )
        startActivity(Intent.createChooser(shareIntent, "Game result"))

    }

    private fun startCreateGameFragment() {
        val creatingGameFragment = CreateGameFragment.newInstance()

        fragmentManager?.beginTransaction()?.replace(R.id.root_container, creatingGameFragment)
            ?.commitAllowingStateLoss()
    }

    private fun getResultText(): String {

        if (firstTeamScore != null && secondTeamScore != null) {
            var strWinner = "Winner — "

            if (firstTeamScore == secondTeamScore) {
                winnerTeamString = "Draw"
                strWinner = winnerTeamString
            } else {
                winnerTeamString = if (firstTeamScore!! > secondTeamScore!!) {
                    firstTeam?.name!!
                } else {
                    secondTeam?.name!!
                }
                strWinner += winnerTeamString
            }

            val strNames = "Game: ${firstTeam?.name} — ${secondTeam?.name}"
            val strScores = "Total score: $firstTeamScore:$secondTeamScore"
            var strTotalGameTime = String()
            totalGameTime?.let {
                strTotalGameTime = "Total game time: ${convertTime(totalGameTime!!)}"
            }
            return "$strWinner\n$strNames\n$strScores\n$strTotalGameTime"
        } else return ""
    }

    private fun printResult() {
        val spannableString = SpannableStringBuilder(getResultText())
        val startIndexWinner = spannableString.indexOf(winnerTeamString)
        val endIndexWinner = startIndexWinner + winnerTeamString.length
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(
            boldSpan,
            startIndexWinner,
            endIndexWinner,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvResultText.text = spannableString
    }

    private fun convertTime(seconds: Int): String {
        when (seconds) {
            1 -> return "$seconds second"
            in 2..59 -> return "$seconds seconds"
        }
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val totalSeconds = seconds % 60
        return if (hours > 1) {
            String.format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                hours,
                minutes,
                totalSeconds
            )
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, totalSeconds)
        }
    }

    companion object {
        fun newInstance(
            firstTeam: Team,
            secondTeam: Team,
            firstTeamScore: Int,
            secondTeamScore: Int,
            totalGameTime: Int
        ) =
            ResultGameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_TEAM_FIRST, firstTeam)
                    putParcelable(ARG_PARAM_TEAM_SECOND, secondTeam)
                    putInt(ARG_PARAM_TEAM_SCORE_FIRST, firstTeamScore)
                    putInt(ARG_PARAM_TEAM_SCORE_SECOND, secondTeamScore)
                    putInt(ARG_PARAM_TOTAL_GAME_TIME, totalGameTime)
                }
            }
    }
}