package com.example.tictactoegame

class Box<T> {
    var content: T? = null

    fun putContent(content:T?){
        this.content = content
    }
    fun retreiveContent():T?{
        return content
    }

    fun isEmpty():Boolean{
        return content == null
    }
}