package com.example.gitspy.database

import android.content.Context
import android.sax.RootElement
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gitspy.models.Item
import com.example.gitspy.models.commits.CommitListItem
import com.example.gitspy.models.issues.Issue

@Database(entities = [Item::class , Issue::class , CommitListItem::class] , version = 1)
//@TypeConverters(Conveters::class)
abstract class TrackedRepoService : RoomDatabase() {

    abstract  fun trackRepoDao() : TrackRepoDao


    companion object{
        @Volatile
        private var instance : TrackedRepoService? = null

        fun getInstance(context: Context) : TrackedRepoService{
            if (instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(context,TrackedRepoService::class.java,"TrackedRepoDB").build()
                }
            }
            return instance!!
        }
    }

}