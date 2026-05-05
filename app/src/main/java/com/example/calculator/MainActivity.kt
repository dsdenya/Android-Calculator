package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.ui.theme.CalculatorTheme
import kotlin.toString

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var opCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var entryClear: Boolean = false

        binding.bOne.setOnClickListener { userClickedBttn("1", false) }
        binding.bTwo.setOnClickListener { userClickedBttn("2", false) }
        binding.bThree.setOnClickListener { userClickedBttn("3", false) }
        binding.bFour.setOnClickListener { userClickedBttn("4", false) }
        binding.bFive.setOnClickListener { userClickedBttn("5", false) }
        binding.bSix.setOnClickListener { userClickedBttn("6", false) }
        binding.bSeven.setOnClickListener { userClickedBttn("7", false) }
        binding.bEight.setOnClickListener { userClickedBttn("8", false) }
        binding.bNine.setOnClickListener { userClickedBttn("9", false) }
        binding.bZero.setOnClickListener { userClickedBttn("0", false) }
        binding.bMultiply.setOnClickListener { userClickedBttn("*", false) }
        binding.bSub.setOnClickListener { userClickedBttn("-", false) }
        binding.bPlus.setOnClickListener { userClickedBttn("+", false) }
        binding.bDiv.setOnClickListener { userClickedBttn("/", false) }
        binding.bEqlSign.setOnClickListener { userClickedSymEql("=") }
        binding.bCE.setOnClickListener { userClickedBttn("", true); }

    }


    fun userClickedSymEql(string: String): Int {
        val userText = binding.editTextText.text.toString()
        val stack = mutableListOf<Int>()
        var res = 0
        var op: Char? = null
        var currentNumber = ""

        for (ch in userText) {
            println("This is current ch: $ch")

            if (ch.isDigit()) {
                currentNumber += ch
                println("This is currentNumber: $currentNumber")
            } else {
                op = ch
                println("This is op: $op")
                if (currentNumber.isNotEmpty()) {
                    stack.add(currentNumber.toInt())
                }
                println("This is stack: $stack")
                currentNumber = ""
            }
        }
        if (currentNumber.isNotEmpty()) {
            stack.add(currentNumber.toInt())
        }

        if (op != null && stack.size > 1) {
            val firstNum = stack.removeAt(stack.size - 1)
            println("This is firstNum: $firstNum")
            val secondNum = stack.removeAt(stack.size - 1)
            println("This is secondNum: $secondNum")

            res = when (op) {
                '+' -> secondNum + firstNum
                '-' -> secondNum - firstNum
                '*' -> secondNum * firstNum
                '/' -> secondNum / firstNum
                else -> 0
            }

            println("Result: $res")
            stack.add(res)
        }

        if (stack.isNotEmpty()) {
            binding.editTextText.setText(stack[0].toString())
            return stack[0]
        } else {
            binding.editTextText.setText("0")
            return 0
        }
    }

    fun userClickedBttn(buttonName: String, clearEntry: Boolean) {
        if (clearEntry) {
            binding.editTextText.setText("")
            opCount = 0
        } else {
            val editTextInput = binding.editTextText.text.toString()
            val newText = editTextInput + buttonName
            if (editTextInput != buttonName) {
                if (buttonName.matches("[+\\-*/]+".toRegex())) {
                    opCount += 1
                    println("opcount: $opCount")

                    if (opCount == 2) {
                        val result = userClickedSymEql(newText)
                        println("Result returned: $result")
                        opCount = 1
                        binding.editTextText.setText(binding.editTextText.text.toString() + buttonName)
                        return
                    }
                }

                binding.editTextText.setText(newText)
            } else{
                binding.editTextText.setText("0")
            }
        }
    }
}











