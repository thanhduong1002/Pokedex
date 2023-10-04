package com.example.pokedex.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.data.Converters
import com.example.pokedex.data.dao.CartDao
import com.example.pokedex.data.dao.FoodDao
import com.example.pokedex.data.dao.TypeDao
import com.example.pokedex.models.Cart
import com.example.pokedex.models.Food
import com.example.pokedex.models.Type

@Database(entities = [Cart::class, Food::class, Type::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun foodDao(): FoodDao
    abstract fun typeDao(): TypeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "pokedex_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
