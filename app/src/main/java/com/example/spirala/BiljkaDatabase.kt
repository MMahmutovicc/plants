package com.example.spirala

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(Converters::class)
abstract class BiljkaDatabase: RoomDatabase() {
    abstract fun biljkaDao() : BiljkaDAO
    @Dao
    interface BiljkaDAO {
        @Insert
        suspend fun insertPlant(biljka: Biljka) : Long
        suspend fun saveBiljka(biljka: Biljka) : Boolean {
            return withContext(Dispatchers.IO) {
                return@withContext insertPlant(biljka) != -1L
            }
        }
        @Update
        suspend fun fixOfflineBiljka(vararg biljka: Biljka) : Int
        @Insert
        suspend fun insertImage(biljkaBitmap: BiljkaBitmap) : Long
        suspend fun addImage(idBiljke : Long, bitmap: Bitmap) : Boolean {
            return withContext(Dispatchers.IO) {
                var biljkaBitmap = BiljkaBitmap(null, bitmap, idBiljke)
                return@withContext insertImage(biljkaBitmap) != -1L
            }
        }
        @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke=:idBiljke")
        suspend fun getImage(idBiljke: Long) : BiljkaBitmap?
        @Query("SELECT * FROM Biljka")
        suspend fun getAllBiljkas() : List<Biljka>
        @Query("DELETE FROM Biljka")
        suspend fun clearData()
    }

    companion object {
        private var INSTANCE : BiljkaDatabase? = null
        fun getInstance(context : Context): BiljkaDatabase {
            if (INSTANCE == null) {
                synchronized(BiljkaDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()
    }
}