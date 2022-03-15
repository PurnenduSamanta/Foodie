package com.example.purnendu

sealed class ResponseHandle<T>(val data: T?=null, val message:String?=null)
{
    class Loading<T>: ResponseHandle<T>()
    class Success<T>(data: T?=null):ResponseHandle<T>(data=data)
    class Error<T>(errorMessage:String):ResponseHandle<T>(message = errorMessage)
}
