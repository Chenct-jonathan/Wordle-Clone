package com.example.wordle109450007

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scanner = Scanner(resources.openRawResource(R.raw.dict))
        val wordlist = HashSet<String>()

        while (scanner.hasNext())
            wordlist.add(scanner.next())
        val answer = wordlist.random()

        AlertDialog.Builder(this)
            .setMessage(answer.uppercase())
            .show()

        for (button in buttons) {
            button.setOnClickListener {
                push(button.text.toString(),answer,wordlist)
            }
        }
    }

    private val buttons: List<Button> by lazy{
        listOf(R.id.buttonQ,R.id.buttonW,R.id.buttonE,R.id.buttonR,R.id.buttonT,R.id.buttonY,R.id.buttonU
            ,R.id.buttonA,R.id.buttonS,R.id.buttonD,R.id.buttonF,R.id.buttonG,R.id.buttonH,R.id.buttonJ
            ,R.id.buttonZ,R.id.buttonX,R.id.buttonC,R.id.buttonV,R.id.buttonB,R.id.buttonN,R.id.buttonM
            ,R.id.buttonI,R.id.buttonO,R.id.buttonP,R.id.buttonK,R.id.buttonL,R.id.buttonEnter,R.id.buttonDelete) .map{findViewById<Button>(it)}
    }


    private val input: List<List<TextView>> by lazy {
        listOf(
            listOf(R.id.input_r1_c1, R.id.input_r1_c2, R.id.input_r1_c3, R.id.input_r1_c4,
                R.id.input_r1_c5).map{ findViewById(it) },
            listOf(R.id.input_r2_c1,R.id.input_r2_c2, R.id.input_r2_c3, R.id.input_r2_c4,
                R.id.input_r2_c5).map{ findViewById(it) },
            listOf(R.id.input_r3_c1,R.id.input_r3_c2, R.id.input_r3_c3, R.id.input_r3_c4,
                R.id.input_r3_c5).map{ findViewById(it) },
            listOf(R.id.input_r4_c1,R.id.input_r4_c2, R.id.input_r4_c3, R.id.input_r4_c4,
                R.id.input_r4_c5).map{ findViewById(it) },
            listOf(R.id.input_r5_c1,R.id.input_r5_c2, R.id.input_r5_c3, R.id.input_r5_c4,
                R.id.input_r5_c5).map{ findViewById(it) },
            listOf(R.id.input_r6_c1,R.id.input_r6_c2, R.id.input_r6_c3, R.id.input_r6_c4,
                R.id.input_r6_c5).map{ findViewById(it) }
        )
    }

    private fun duplicateCheck(str: String): Boolean {
        for (i in str.indices)
            for (j in i+1 until str.length)
                if (str[i] == str[j])
                    return false
        return true
    }

    private fun repeatCheck(str: String): Set<Char> {
        var repeats = mutableSetOf<Char>()
        for (s in str) {
            var count = 0
            var keycount=0
            for (i in 0..4) {
                if (str[i] == s) {
                    count ++
                }
            }
            if (count > 1 ) {
                repeats += s
            }
        }
        return repeats
    }

    private var attempts = 6
    private var curRow = 0
    private var curCol = 0

    private fun push(c: String, answer: String, wordlist: HashSet<String>) {
        if(attempts == 0){
            AlertDialog.Builder(this).setMessage(
                "Failed")
                .show()
        }
        else{
            if(curRow < 6){
                when {
                    c == "ENTER" -> {
                        if(curCol != 5){
                            AlertDialog.Builder(this).setMessage(
                                "No Enough Letters")
                                .show()
                        }
                        if (curCol == 5) {
                            if("${input[curRow].map{ it.text }.joinToString("").lowercase()}" in wordlist) {
                                if("${input[curRow].map{ it.text }.joinToString("").lowercase()}" == answer){
                                    AlertDialog.Builder(this).setMessage(
                                        "Congrats! You Won!")
                                        .show()
                                    attempts=0
                                    for(i in 0..4){
                                        input[curRow][i].setBackgroundColor(Color.parseColor("#009933"))
                                    }
                                }

                                if("${input[curRow].map{ it.text }.joinToString("").lowercase()}" != answer){
                                    val guess = input[curRow].map{it.text}.joinToString("").lowercase()
                                    for(i in 0..4){
                                        if(guess[i]== answer[i]){
                                            input[curRow][i].setBackgroundColor(Color.parseColor("#009933"))
                                        }

                                        else if(guess[i] !in answer){
                                            input[curRow][i].setBackgroundColor(Color.parseColor("#666666"))
                                        }
                                        else{
                                            input[curRow][i].setBackgroundColor(Color.parseColor("#ffff00"))
                                        }
                                    }


                                    if(duplicateCheck(guess)==false){

                                        for(i in 0..4){
                                            if(guess[i]== answer[i]){
                                                input[curRow][i].setBackgroundColor(Color.parseColor("#009933"))
                                            }

                                            else if(guess[i] !in answer){
                                                input[curRow][i].setBackgroundColor(Color.parseColor("#666666"))
                                            }
                                            else{
                                                input[curRow][i].setBackgroundColor(Color.parseColor("#ffff00"))
                                            }
                                        }

                                        for(s in repeatCheck(guess)) {
                                            var keycount = 0
                                            for (i in 0..4) {
                                                if (answer[i] == s) {
                                                    keycount++
                                                }
                                            }
                                            var repeatIndex = mutableSetOf<Int>()
                                            var repeatWrongIndex = mutableSetOf<Int>()
                                            for (i in 0..4) {
                                                if (guess[i] == s && guess[i] == answer[i]) {
                                                    repeatIndex += i
                                                }
                                                else if (guess[i] == s && guess[i] != answer[i]) {
                                                    repeatWrongIndex += i
                                                }
                                            }

                                            if(s in answer){
                                                if (repeatIndex.size <= keycount) {
                                                    var yellowTimes = keycount - repeatIndex.size
                                                    for (i in 0..4) {
                                                        if(guess[i] == s){
                                                            if (yellowTimes >= 1 && guess[i] != answer[i]) {
                                                                input[curRow][i].setBackgroundColor(
                                                                    Color.parseColor("#ffff00")
                                                                )
                                                                yellowTimes--
                                                            }
                                                            else if (yellowTimes == 0) {
                                                                if(guess[i] == s && guess[i] == answer[i]){
                                                                    input[curRow][i].setBackgroundColor(
                                                                        Color.parseColor("#009933")
                                                                    )
                                                                }
                                                                else if (guess[i] == s) {
                                                                    input[curRow][i].setBackgroundColor(
                                                                        Color.parseColor("#666666")
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    attempts --
                                    curRow ++
                                    curCol = 0
                                }
                            }
                            else{
                                AlertDialog.Builder(this).setMessage(
                                    "Word Not In Word list")
                                    .show()
                            }
                        }
                    }
                    c == "DELETE" -> {
                        if (curCol > 0) {
                            curCol--
                            input[curRow][curCol].text = ""
                        }
                    }
                    else -> {
                        if (curCol < 5) {
                            input[curRow][curCol].text = c
                            curCol++
                        }
                    }
                }
            }
        }
    }
}