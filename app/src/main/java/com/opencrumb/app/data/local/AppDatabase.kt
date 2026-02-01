package com.opencrumb.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.opencrumb.app.data.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

@Database(entities = [Recipe::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "recipe_database",
                        ).fallbackToDestructiveMigration()
                        .addCallback(AppDatabaseCallback(context.applicationContext))
                        .build()
                INSTANCE = instance
                instance
            }
    }

    private class AppDatabaseCallback(
        private val context: Context,
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            // onOpen will handle population, so this can be empty.
            super.onCreate(db)
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            // Wiping and repopulating on every app start for development purposes.
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.recipeDao())
                }
            }
        }

        private suspend fun populateDatabase(recipeDao: RecipeDao) {
            // Wipe all data and repopulate from JSON
            // TODO undo this before launch
            recipeDao.deleteAll()

            val jsonString: String
            try {
                jsonString =
                    context.assets
                        .open("recipes.json")
                        .bufferedReader()
                        .use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return
            }

            val listRecipeType = object : TypeToken<List<Recipe>>() {}.type
            val recipes: List<Recipe> = Gson().fromJson(jsonString, listRecipeType)

            recipeDao.insertAll(recipes)
        }
    }
}
