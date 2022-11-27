package com.example.tictactoegame.queue

class ArrayListQueue<T>:Queue<T> {
    private val list = arrayListOf<T>()
    override fun enqueue(e: T): Boolean {
        list.add(e)
        return true
    }

    override fun dequeue(): T? = if(isEmpty) null else list.removeAt(0)

    override fun toString(): String = list.toString()
    override val count: Int
        get() = list.size

    override fun peek(): T? = list.getOrNull(0)

}