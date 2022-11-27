package com.example.tictactoegame

interface Stack<T:Any> {
    fun push(e:T)
    fun pop():T?
    fun peek(): T?

    val count: Int
        get

    val isEmpty: Boolean
        get() = count == 0
}

class StackImpl<T:Any>():Stack<T>{
    private val storage = arrayListOf<T>()

    override fun toString() = buildString {
        appendLine("-----top-----")
        storage.asReversed().forEach {
            append("$it")
        }
        appendLine("----------")
    }

    override fun push(e:T){
        storage.add(e)
    }

    override fun pop():T?{
        if(storage.size==0){
            return null
        }
        return storage.removeAt(storage.size-1)
    }

    override fun peek(): T? {
        return storage.lastOrNull()
    }

    override val count: Int
        get() = storage.size

}
