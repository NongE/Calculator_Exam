package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var operatorStack = mutableListOf<String>()
    private var numberStack = mutableListOf<Any>()
    private var bracketFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // button layout view binding
        val includeButtonView = binding.IncludeButton

        // number listener
        includeButtonView.btn0.setOnClickListener{
            appendOnClick(false, "0")
        }
        includeButtonView.btn1.setOnClickListener{
            appendOnClick(false, "1")
        }
        includeButtonView.btn2.setOnClickListener{
            appendOnClick(false, "2")
        }
        includeButtonView.btn3.setOnClickListener{
            appendOnClick(false, "3")
        }
        includeButtonView.btn4.setOnClickListener{
            appendOnClick(false, "4")
        }
        includeButtonView.btn5.setOnClickListener{
            appendOnClick(false, "5")
        }
        includeButtonView.btn6.setOnClickListener{
            appendOnClick(false, "6")
        }
        includeButtonView.btn7.setOnClickListener{
            appendOnClick(false, "7")
        }
        includeButtonView.btn8.setOnClickListener{
            appendOnClick(false, "8")
        }
        includeButtonView.btn9.setOnClickListener{
            appendOnClick(false, "9")
        }

        // operator listener
        includeButtonView.btnBracket.setOnClickListener{
            when (bracketFlag){
                false -> {
                    appendOnClick(true,"(")
                    bracketFlag = true
                }
                true -> {
                    appendOnClick(true,")")
                    bracketFlag = false
                }
            }

        }
        includeButtonView.btnPercent.setOnClickListener{
            appendOnClick(true,"%")
        }
        includeButtonView.btnDivsion.setOnClickListener{
            appendOnClick(true,"/")
        }
        includeButtonView.btnMulti.setOnClickListener{
            appendOnClick(true,"*")
        }
        includeButtonView.btnMinus.setOnClickListener{
            appendOnClick(true,"-")
        }
        includeButtonView.btnPlus.setOnClickListener{
            appendOnClick(true,"+")
        }
        includeButtonView.btnDot.setOnClickListener{
            appendOnClick(true,".")
        }

        includeButtonView.btnClear.setOnClickListener{
            clear()
        }

        includeButtonView.btnEqual.setOnClickListener{
            calculate()
        }

    }

    private fun appendOnClick(operatorFlag: Boolean, string:String){
        val includeInputLayout = binding.IncludeInputLayout
        includeInputLayout.userInput.append(string)

        if (operatorFlag){

            when (string){
                ")" -> {
                    operatorStack.add(string)
                    operatorStack.remove("(")
                    operatorStack.remove(")")
                    operatorStack.reverse()
                    numberStack.addAll(operatorStack)
                    operatorStack.clear()
                }
                else -> {
                    operatorStack.add(string)
                }
            }

        }else{
            numberStack.add(string.toInt())
        }

    }

    private fun clear(){
        val includeInputLayout = binding.IncludeInputLayout
        includeInputLayout.userInput.text = ""
        includeInputLayout.userOutput.text = ""
        operatorStack.clear()
        numberStack.clear()
        bracketFlag = false
    }

    private fun calculate(){

        var calculateStack = mutableListOf<Int>()
        kotlin.runCatching {
            numberStack.addAll(operatorStack)

            Log.d("calculateLog", "is operator ${operatorStack}")
            Log.d("calculateLog", "is operator ${numberStack}")
            for(index in 0..numberStack.size-1){
                if(numberStack[index] is Int) {
                    calculateStack.add(numberStack[index] as Int)
                }else{
                    var rangeOfCalculateStack = calculateStack.size
                    when (numberStack[index]){
                        "+" -> {
                            val calTemp = calculateStack[rangeOfCalculateStack-1] + calculateStack[rangeOfCalculateStack-2]
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.add(calTemp)
                            Log.d("calculateLog", "active + is ${calculateStack[0]}")
                        }
                        "-" -> {
                            val calTemp = calculateStack[rangeOfCalculateStack-1] - calculateStack[rangeOfCalculateStack-2]
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.add(calTemp)
                            Log.d("calculateLog", "active - is ${calculateStack[0]}")
                        }
                        "*" -> {
                            val calTemp = calculateStack[rangeOfCalculateStack-1] * calculateStack[rangeOfCalculateStack-2]
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.add(calTemp)
                            Log.d("calculateLog", "active * is ${calculateStack[0]}")
                        }
                        "/" -> {
                            val calTemp = calculateStack[rangeOfCalculateStack-1] / calculateStack[rangeOfCalculateStack-2]
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.removeAt(rangeOfCalculateStack-1)
                            rangeOfCalculateStack -= 1
                            calculateStack.add(calTemp)
                            Log.d("calculateLog", "active / is ${calculateStack[0]}")
                        }
                    }
                }
            }
            binding.IncludeInputLayout.userOutput.text = "${calculateStack[0]}"
        }.onFailure {e ->
            Log.d("calculateLog", "$e")
            Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }

}