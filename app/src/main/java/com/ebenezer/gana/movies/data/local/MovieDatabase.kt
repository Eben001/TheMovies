package com.ebenezer.gana.movies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ebenezer.gana.movies.data.local.typeConverters.DatabaseTypeConverters
import com.ebenezer.gana.movies.data.Movie
import com.ebenezer.gana.movies.data.local.dao.MovieDetailDao
import com.ebenezer.gana.movies.data.local.dao.MovieListDao

@TypeConverters(DatabaseTypeConverters::class)
@Database(
    entities = [Movie::class],
    version = 1, exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieListDao(): MovieListDao
    abstract fun movieDetailsDao(): MovieDetailDao

    companion object {
        private const val DATABASE_NAME = "movie_database"

        /* the value of a volatile variable will never be cached, all writes and reads will be
                 from the the main memory*/
        @Volatile
        private var INSTANCE: MovieDatabase? = null
        fun getDatabase(context: Context): MovieDatabase {


            /* wrapping to get the database inside the synchronized block means
             only one thread of execution at a time can enter this block of code*/
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                INSTANCE = instance

                return instance
            }
        }


    }


}