package com.example.practico_2.listener

import com.example.practico_2.model.ComidaModel

interface IDcomidaLoadListener {
    fun onComidaLoadSuccess(comidaModelList: List<ComidaModel>?)
    fun onComidaLoadFailed(message:String?)

}