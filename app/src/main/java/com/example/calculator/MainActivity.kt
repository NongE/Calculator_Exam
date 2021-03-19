package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var operatorStack = KotlinStack()
    private var numberStack = mutableListOf<Any>()
    private var bracketFlag = false
    private var numberFlag = false

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
            bracketFlag = when (bracketFlag){
                false -> {
                    appendOnClick(true,"(")
                    true
                }
                true -> {
                    appendOnClick(true,")")
                    false
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
                    operatorStack.push(string)
                    operatorStack.remove("(")
                    operatorStack.remove(")")
                    operatorStack.reverse()
                    numberStack.addAll(operatorStack.get())
                    operatorStack.clear()
                }
                else -> {
                    operatorStack.push(string)
                }

            }
            numberFlag = true

        }else{
            if (numberStack.size != 0 && !numberFlag){
                var tmp = numberStack[numberStack.size-1]
                numberStack.removeAt(numberStack.size-1)
                numberStack.add((tmp.toString() + string).toInt())

            } else{
                numberStack.add(string.toInt())
                numberFlag = false
            }
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

        val calculateStack = KotlinStack()
        kotlin.runCatching {
            operatorStack.reverse()
            numberStack.addAll(operatorStack.get())

            Log.d("calculateLog", "is operator $numberStack")
            for(index in 0 until numberStack.size){
                if(numberStack[index] is Int) {
                    calculateStack.push(numberStack[index] as Int)
                    Log.d("calculateLog", "is push ${calculateStack.get()}")
                }else{
                    when (numberStack[index]){
                        "+" -> {
                            val calTemp = calculateStack.pop() as Int + calculateStack.pop() as Int
                            Log.d("calculateLog", "is + $calTemp")
                            calculateStack.push(calTemp)
                        }
                        "-" -> {
                            val calTemp = calculateStack.pop() as Int - calculateStack.pop() as Int
                            Log.d("calculateLog", "is - $calTemp")
                            calculateStack.push(calTemp)
                        }
                        "*" -> {
                            val calTemp = calculateStack.pop() as Int * calculateStack.pop() as Int
                            Log.d("calculateLog", "is + $calTemp")
                            calculateStack.push(calTemp)
                        }
                        "/" -> {
                            val calTemp = calculateStack.pop() as Int / calculateStack.pop() as Int
                            calculateStack.push(calTemp)
                        }
                    }
                }
            }
            binding.IncludeInputLayout.userOutput.text = "${calculateStack.get()[0]}"
        }.onFailure {e ->
            Log.d("calculateLog", "$e")
            Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }

}