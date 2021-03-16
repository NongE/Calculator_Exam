package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // button layout view binding
        val includeButtonView = binding.IncludeButton

        // number listener
        includeButtonView.btn0.setOnClickListener{
            appendOnClick("0")
        }
        includeButtonView.btn1.setOnClickListener{
            appendOnClick("1")
        }
        includeButtonView.btn2.setOnClickListener{
            appendOnClick("2")
        }
        includeButtonView.btn3.setOnClickListener{
            appendOnClick("3")
        }
        includeButtonView.btn4.setOnClickListener{
            appendOnClick("4")
        }
        includeButtonView.btn5.setOnClickListener{
            appendOnClick("5")
        }
        includeButtonView.btn6.setOnClickListener{
            appendOnClick("6")
        }
        includeButtonView.btn7.setOnClickListener{
            appendOnClick("7")
        }
        includeButtonView.btn8.setOnClickListener{
            appendOnClick("8")
        }
        includeButtonView.btn9.setOnClickListener{
            appendOnClick("9")
        }

        // operator listener
        includeButtonView.btnBracket.setOnClickListener{
            appendOnClick("()")
        }
        includeButtonView.btnPercent.setOnClickListener{
            appendOnClick("%")
        }
        includeButtonView.btnDivsion.setOnClickListener{
            appendOnClick("/")
        }
        includeButtonView.btnMulti.setOnClickListener{
            appendOnClick("*")
        }
        includeButtonView.btnMinus.setOnClickListener{
            appendOnClick("-")
        }
        includeButtonView.btnPlus.setOnClickListener{
            appendOnClick("+")
        }
        includeButtonView.btnDot.setOnClickListener{
            appendOnClick(".")
        }

        includeButtonView.btnClear.setOnClickListener{
            clear()
        }

        includeButtonView.btnEqual.setOnClickListener{
            calculate()
        }

    }

    private fun appendOnClick(string:String){
        val includeInputLayout = binding.IncludeInputLayout
        includeInputLayout.userInput.append(string)
    }

    private fun clear(){
        val includeInputLayout = binding.IncludeInputLayout
        includeInputLayout.userInput.text = ""
        includeInputLayout.userOutput.text = ""
    }

    private fun calculate(){
        kotlin.runCatching {

        }.onFailure {e ->
            Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }

}