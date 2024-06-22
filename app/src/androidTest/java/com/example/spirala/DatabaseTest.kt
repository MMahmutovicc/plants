package com.example.spirala

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var biljkaDao: BiljkaDatabase.BiljkaDAO
    private lateinit var db: BiljkaDatabase
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BiljkaDatabase::class.java).build()
        biljkaDao = db.biljkaDao()
    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    @Test
    @Throws(Exception::class)
    fun addBiljkaAndRetrieve() = runBlocking {
        val biljka = Biljka(
            "Common nettle",
            "Urticaceae",
            "NIJE JESTIVO",
            listOf(MedicinskaKorist.REGULACIJAPROBAVE),
            ProfilOkusaBiljke.GORKO,
            listOf("jelo"),
            listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            listOf(Zemljiste.KRECNJACKO, Zemljiste.GLINENO),
            false,
            1
        )
        val dodano = biljkaDao.saveBiljka(biljka)
        val biljke = biljkaDao.getAllBiljkas()
        assertThat(biljke[0], equalTo(biljka))
    }
    @Test
    @Throws(Exception::class)
    fun clearData() = runBlocking {
        val biljka = Biljka(
            "Common nettle",
            "Urticaceae",
            "NIJE JESTIVO",
            listOf(MedicinskaKorist.REGULACIJAPROBAVE),
            ProfilOkusaBiljke.GORKO,
            listOf("jelo"),
            listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            listOf(Zemljiste.KRECNJACKO, Zemljiste.GLINENO),
            false,
            1
        )
        val dodano = biljkaDao.saveBiljka(biljka)
        val biljke1 = biljkaDao.getAllBiljkas()
        biljkaDao.clearData()
        val biljke = biljkaDao.getAllBiljkas()
        assertThat(biljke1[0], equalTo(biljka))
        assertThat(biljke, equalTo(emptyList()))
    }
}