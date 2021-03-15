package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.button.*
import kotlinx.android.synthetic.main.input_layout.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // number listener
        btn0.setOnClickListener{
            appendOnClick("0")
        }
        btn1.setOnClickListener{
            appendOnClick("1")
        }
        btn2.setOnClickListener{
            appendOnClick("2")
        }
        btn3.setOnClickListener{
            appendOnClick("3")
        }
        btn4.setOnClickListener{
            appendOnClick("4")
        }
        btn5.setOnClickListener{
            appendOnClick("5")
        }
        btn6.setOnClickListener{
            appendOnClick("6")
        }
        btn7.setOnClickListener{
            appendOnClick("7")
        }
        btn8.setOnClickListener{
            appendOnClick("8")
        }
        btn9.setOnClickListener{
            appendOnClick("9")
        }

        // operator listener
        btnBracket.setOnClickListener{
            appendOnClick("()")
        }
        btnPercent.setOnClickListener{
            appendOnClick("%")
        }
        btnDivsion.setOnClickListener{
            appendOnClick("/")
        }
        btnMulti.setOnClickListener{
            appendOnClick("*")
        }
        btnMinus.setOnClickListener{
            appendOnClick("-")
        }
        btnPlus.setOnClickListener{
            appendOnClick("+")
        }
        btnDot.setOnClickListener{
            appendOnClick(".")
        }

        btnClear.setOnClickListener{
            clear()
        }

        btnEqual.setOnClickListener{
            calculate()
        }

    }

    fun appendOnClick(string:String){
        userInput.append(string)
    }

    fun clear(){
        userInput.text = ""
        userOutput.text = ""
    }

    fun calculate(){
        try{

        }catch (e:Exception){
            Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }
}