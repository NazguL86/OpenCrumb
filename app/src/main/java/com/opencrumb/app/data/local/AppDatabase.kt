package com.opencrumb.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.opencrumb.app.data.SampleData
import com.opencrumb.app.data.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Recipe::class], version = 3, exportSchema = false)
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
                        .addCallback(AppDatabaseCallback(context))
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
            // Wipe all data and repopulate from SampleData
            recipeDao.deleteAll()
            recipeDao.insertAll(SampleData.recipes)
        }
    }
}
