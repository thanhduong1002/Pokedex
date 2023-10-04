package com.example.pokedex.data.repository

import com.example.pokedex.data.dao.TypeDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.models.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TypeRepository constructor(
    private val typeDao: TypeDao,
    private val database: AppDatabase
) {
    fun insertTypes(listTypes: List<Type>) {
        CoroutineScope(Dispatchers.IO).launch {
            database.typeDao().insertAllTypes(listTypes)
        }
    }

    fun getAllTypes(): List<Type> = typeDao.getAllTypes()
}