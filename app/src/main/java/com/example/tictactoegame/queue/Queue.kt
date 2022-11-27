package com.example.tictactoegame.queue

interface Queue<T> {
    fun enqueue(e:T):Boolean
    fun dequeue():T?
    val count:Int
        get

    val isEmpty:Boolean
        get() = count==0

    fun peek():T?
}