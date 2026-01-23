package com.opencrumb.app

import android.app.Application
import com.opencrumb.app.data.RecipeRepository
import com.opencrumb.app.data.local.AppDatabase

class OpenCrumbApplication : Application() {
    // Using by lazy so the database and repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { RecipeRepository(database.recipeDao()) }
}
