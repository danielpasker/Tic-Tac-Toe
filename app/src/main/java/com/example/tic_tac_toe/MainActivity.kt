package com.example.tic_tac_toe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.color.DynamicColors

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var textViewPlayer: TextView
    private lateinit var resetButton: Button

    private var currentPlayer = "X"
    private var moves = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivityIfAvailable(this)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        this.textViewPlayer = findViewById(R.id.text_view_player)
        this.updateCurrentPlayerText()

        this.buttons = Array(3) { row ->
            Array(3) { col ->
                findViewById<Button>(
                    resources.getIdentifier("button_${row}${col}", "id", packageName)
                )
            }
        }

        for (i in 0..2) {
            for (j in 0..2) {
                this.buttons[i][j].setOnClickListener { onButtonClick(it) }
            }
        }

        resetButton = findViewById<Button>(R.id.button_reset).apply {
            setOnClickListener { resetBoard() }
        }
    }

    private fun onButtonClick(view: View) {
        if (view !is Button || view.text != "") return

        view.text = currentPlayer
        moves++

        if (checkForWin()) {
            this.handleWin()

            return
        }

        if (moves == 9) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
            this.textViewPlayer.text = getString(R.string.game_over_text)

            return
        }

        currentPlayer = if (currentPlayer == "X") "O" else "X"
        this.updateCurrentPlayerText()
    }

    private fun checkForWin(): Boolean {
        for (i in 0..2) {
            if (buttons[i][0].text == currentPlayer &&
                buttons[i][1].text == currentPlayer &&
                buttons[i][2].text == currentPlayer
            ) {
                return true
            }
            if (buttons[0][i].text == currentPlayer &&
                buttons[1][i].text == currentPlayer &&
                buttons[2][i].text == currentPlayer
            ) {
                return true
            }
        }

        if (buttons[0][0].text == currentPlayer &&
            buttons[1][1].text == currentPlayer &&
            buttons[2][2].text == currentPlayer
        ) {
            return true
        }

        if (buttons[0][2].text == currentPlayer &&
            buttons[1][1].text == currentPlayer &&
            buttons[2][0].text == currentPlayer
        ) {
            return true
        }

        return false
    }

    private fun handleWin() {
        Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT).show()
        this.setButtonsEnabled(false)
        this.resetButton.text = getString(R.string.button_play_again)
        this.textViewPlayer.text = getString(R.string.game_over_text)
    }

    private fun setButtonsEnabled(isEnabled: Boolean) {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = isEnabled

                if (isEnabled) {
                    buttons[i][j].text = ""
                }
            }
        }
    }

    private fun resetBoard() {
        currentPlayer = "X"
        moves = 0
        this.setButtonsEnabled(true)
        this.updateCurrentPlayerText()
    }

    private fun updateCurrentPlayerText() {
        textViewPlayer.text = getString(R.string.turn_text, currentPlayer)
    }
}