package com.example.politi_cal.models

data class CallBack<T, K>(
    private var input: T,
    private var output: K?,
    private var called: Boolean
){
    constructor(input_value: T): this(
        input =input_value,
        called = false, output = null)

    fun Call(){
        this.called=true
    }

    fun getInput(): T? {
        return input
    }

    fun setOutput(value: K?){
        output=value
    }

    fun getOutput(): K? {
        if(!called){
            return null
        }
        return output
    }

    fun getStatus():Boolean{
        return this.called
    }
}
