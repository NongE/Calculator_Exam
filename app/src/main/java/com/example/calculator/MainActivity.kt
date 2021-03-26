package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var operatorStack = KotlinStack()
    private var numberStack = mutableListOf<String>()
    private var numberStack_tmp = mutableListOf<String>()
    private val calculateStack = KotlinStack()
    private var bracketFlag = false
    private var numberFlag = false
    private var calculateFlag = false

    private val operatorArray = arrayListOf<String>("+", "-", "*", "/", "%")
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
            appendOnClick(false,".")
        }

        includeButtonView.btnClear.setOnClickListener{
            clear()
        }

        includeButtonView.btnEqual.setOnClickListener{

            val tmp = calculateStack.get()[0]
            val includeInputLayout = binding.IncludeInputLayout

            if ((tmp.toFloatOrNull() ?: 1F) % 1 == 0F){
                clear()
                includeInputLayout.userInput.text = tmp.toFloat().toInt().toString()
                numberStack.add(tmp)
            }else{
                clear()
                includeInputLayout.userInput.text = tmp
                numberStack.add(tmp)
            }



        }

    }

    private fun appendOnClick(operatorFlag: Boolean, string:String){
        val includeInputLayout = binding.IncludeInputLayout


        if (operatorFlag){
            if (calculateFlag){
                var tmp = includeInputLayout.userInput.text.toString()
                tmp = tmp.toCharArray().dropLast(1).joinToString(separator = "")
                Log.d("calculateLog", "is change operator ${calculateStack.get()}")
                includeInputLayout.userInput.text = tmp
                operatorStack.pop()
                operatorStack.push(string)

            }else {
                when (string) {
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
                        calculateFlag = true
                    }

                }

            }
            includeInputLayout.userInput.append(string)
            numberFlag = true

        }else{
            if (numberStack.size != 0 && !numberFlag){
                val tmp = numberStack[numberStack.size-1]
                numberStack.removeAt(numberStack.size-1)
                numberStack.add((tmp + string))

            } else{
                numberStack.add(string)
                numberFlag = false
            }
            if (calculateFlag) {
                calculate()
                calculateFlag = false
            }

            includeInputLayout.userInput.append(string)

            var seperateTemp = includeInputLayout.userInput.text.split("")
            seperateTemp = seperateTemp.drop(1).dropLast(1)
            Log.d("calculateLog", "is seperate ${seperateTemp}")

            for(index in seperateTemp.indices){
                if(numberStack_tmp.size != 0){
                    kotlin.runCatching {
                        if (seperateTemp[index] == "0"){
                            Log.d("calculateLog", "is zero!")
                            numberStack_tmp[numberStack_tmp.size-1] = numberStack_tmp[numberStack_tmp.size-1] + seperateTemp[index]
                        }else {
                            val t = seperateTemp[index].toFloat()
                            if (numberStack_tmp[numberStack_tmp.size-1] !in operatorArray) {
                                numberStack_tmp[numberStack_tmp.size-1] = numberStack_tmp[numberStack_tmp.size-1] + seperateTemp[index]
                            }else{
                                numberStack_tmp.add(seperateTemp[index])
                            }
                        }
                    }.onFailure {e ->
                        Log.d("calculateLog", "except! index is $index")
                        numberStack_tmp.add(seperateTemp[index])
                    }
                }else{
                    numberStack_tmp.add(seperateTemp[index])
                }
            }
            numberStack_tmp.removeAll(operatorArray)
            Log.d("calculateLog", "is numberStack_tmp ${numberStack_tmp}")
            numberStack_tmp.clear()

            Log.d("calculateLog", "is click calculate ${calculateStack.get()}")
        }


    }

    private fun clear(){

        val includeInputLayout = binding.IncludeInputLayout
        includeInputLayout.userInput.text = ""
        includeInputLayout.userOutput.text = ""
        operatorStack.clear()
        numberStack.clear()
        calculateStack.clear()
        bracketFlag = false
        numberFlag = false
        calculateFlag = false
    }

    private fun calculate(){

            Log.d("calculateLog", "is operator ${operatorStack.get()}")
            Log.d("calculateLog", "is number $numberStack")
            Log.d("calculateLog", "is calculate ${calculateStack.get()}")
            kotlin.runCatching {
                operatorStack.reverse()
                numberStack.addAll(operatorStack.get())
                Log.d("calculateLog", "is number $numberStack")
                for(index in 0 until numberStack.size){
                    if(numberStack[index] !in operatorArray) {
                        calculateStack.push(numberStack[index])
                    }else{
                        when (numberStack[index]){
                            "+" -> {
                                val calTemp = (calculateStack.pop().toFloatOrNull() ?: 0F) + (calculateStack.pop().toFloatOrNull() ?: 0F)
                                calculateStack.push(calTemp.toString())
                            }
                            "-" -> {
                                val t1 = calculateStack.pop().toFloatOrNull() ?: 1F
                                val t2 = calculateStack.pop().toFloatOrNull() ?: 1F
                                val calTemp = t2 - t1
                                calculateStack.push(calTemp.toString())
                            }
                            "*" -> {
                                val calTemp = (calculateStack.pop().toFloatOrNull() ?: 0F) * (calculateStack.pop().toFloatOrNull() ?: 0F)
                                calculateStack.push(calTemp.toString())
                            }
                            "/" -> {
                                val t1 = calculateStack.pop().toFloatOrNull() ?: 1F
                                val t2 = calculateStack.pop().toFloatOrNull() ?: 1F
                                val calTemp = t2 / t1
                                calculateStack.push(calTemp.toString())
                            }
                        }
                    }
                }

                if ((calculateStack.get()[0].toFloatOrNull() ?: 1F) % 1 == 0F){
                    Log.d("calculateLog", "is ${((calculateStack.get()[0].toFloat()) % 1).toInt()}")
                    binding.IncludeInputLayout.userOutput.text = calculateStack.get()[0].toFloat().toInt().toString()
                }else{
                    binding.IncludeInputLayout.userOutput.text = calculateStack.get()[0]
                }

                //calculateStack.clear()

        }.onFailure {e ->
            Log.d("calculateLog", "$e")
            Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
            clear()
        }
    }

}