package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.models.Type
import com.example.pokedex.data.repository.TypeRepository

class TypeViewModel(private var typeRepository: TypeRepository): ViewModel() {
    private val _typeList: MutableLiveData<List<Type>> = MutableLiveData(emptyList())
    val typeList: LiveData<List<Type>> = _typeList

    private val _updatedList: MutableLiveData<Boolean> = MutableLiveData()
    val updatedList: LiveData<Boolean> = _updatedList

    fun insertAll(listTypes: List<Type>) {
        _typeList.postValue(listTypes)
        typeRepository.insertTypes(listTypes)
    }

    fun getAllTypes() {
        val typeList: List<Type> = typeRepository.getAllTypes()
        _typeList.postValue(typeList)
    }
}