package com.example.braintainer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.braintainer.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var answers: ArrayList<Int> = ArrayList()
    private var locationOfCorrectAnswer: Int = 0

    var score: Int = 0
    var numberOfQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateQuestions()
        playAgain()
        binding.TimerTextView.text = "30s"
        binding.pointsTextView.text = "0/0"
        binding.resultTextView.text = ""

        binding.buttonPlayAgain.setOnClickListener {
            playAgain()
        }

    }

    fun start(view:View) {
        binding.startButton.visibility = View.INVISIBLE
    }

    @SuppressLint("LongLogTag")
    fun chooseAnswer(view: View) {
        if (view.tag.toString() == locationOfCorrectAnswer.toString()) {
            Log.d("Printing5,btn,correctAnswer", view.toString())
            score++
            binding.resultTextView.text = "Correct Answer"
        }
        else {
            binding.resultTextView.text = "Incorrect Answer"
        }
        numberOfQuestions++
        binding.pointsTextView.text = "$score/$numberOfQuestions"

        generateQuestions()

    }

    @SuppressLint("LongLogTag")
    private fun generateQuestions() {
        val random = Random()
        val a = random.nextInt(21)
        Log.d("Printing,a", a.toString())
        val b = random.nextInt(21)
        Log.d("Printing1,b", b.toString())

        binding.sumTextView.text = "$a + $b"

        answers.clear()

        locationOfCorrectAnswer = random.nextInt(4)
        for(i in 0..4) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a+b)
                Log.d("Printing2,correctAnswer", i.toString())
            } else {
                var inCorrectAnswer = random.nextInt(41)

                while (inCorrectAnswer == a+b) {
                    inCorrectAnswer = random.nextInt(41)
                }
                answers.add(inCorrectAnswer)
                Log.d("Printing3,incorrectAnswer", i.toString())
            }
            Log.d("Printing4,Arrays", answers.toString())
        }

        binding.button1.text = answers[0].toString()
        binding.button2.text = answers[1].toString()
        binding.button3.text = answers[2].toString()
        binding.button4.text = answers[3].toString()
    }

    private fun timer(){
        object  : CountDownTimer(20100,1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.TimerTextView.text = (millisUntilFinished/1000).toString() + "s"
            }

            override fun onFinish() {
                binding.buttonPlayAgain.visibility = View.VISIBLE
                binding.TimerTextView.text = "0s"
                binding.resultTextView.text = "Your Score: ${score/numberOfQuestions}"
            }

        }.start()
    }

    private fun playAgain() {
        score = 0
        numberOfQuestions = 0
        generateQuestions()
        binding.buttonPlayAgain.visibility = View.INVISIBLE
        timer()
    }
}