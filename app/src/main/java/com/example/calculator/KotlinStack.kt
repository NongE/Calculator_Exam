package com.example.calculator

class KotlinStack() {

        private val stack = mutableListOf<String>()

        fun get():MutableList<String>{
            return stack
        }
        fun size(): Int{
            return stack.size
        }
        fun push(item: String){
            stack.add(item)
        }
        fun pop(): String{
            val tmp  = stack[stack.size-1]
            stack.removeAt(stack.size-1)
            return tmp
        }

        fun remove(item: String){
            stack.remove(item)
        }

        fun reverse(){
            return stack.reverse()
        }

        fun clear(){
            stack.clear()
        }

}