package com.example.tictactoegame.linkedlist

data class Node<T>(val value:T, var next: Node<T>? = null) {
    init {

    }
    override fun toString(): String {
        //print("tostring")
        return if(next!=null){
            "$value -> ${next.toString()}"
        }else{
            "$value"
        }
    }
}