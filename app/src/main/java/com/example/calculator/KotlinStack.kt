package com.example.calculator

class KotlinStack() {

        private val stack = mutableListOf<Any>()

        fun get():MutableList<Any>{
            return stack
        }
        fun size(): Int{
            return stack.size
        }
        fun push(item: Any){
            stack.add(item)
        }
        fun pop(): Any{
            val tmp  = stack[stack.size-1]
            stack.removeAt(stack.size-1)
            return tmp
        }

        fun remove(item: Any){
            stack.remove(item)
        }

        fun reverse(){
            return stack.reverse()
        }

        fun clear(){
            stack.clear()
        }

}