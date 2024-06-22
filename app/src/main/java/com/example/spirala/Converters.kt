package com.example.spirala

import android.R.attr
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import java.nio.ByteBuffer
import java.util.Arrays


class Converters {
    @TypeConverter
    fun medicinskaKoristToString(medicinskeKoristi: List<MedicinskaKorist>) : String {
        return medicinskeKoristi.joinToString (separator = ",")
    }
    @TypeConverter
    fun stringToMedicinskaKorist(value: String) : List<MedicinskaKorist> {
        val values: List<String> = value.split(",").map { it.trim() }
        val medicinskeKoristi = mutableListOf<MedicinskaKorist>()

        for (s in values) medicinskeKoristi.add(MedicinskaKorist.valueOf(s))

        return medicinskeKoristi
    }

    @TypeConverter
    fun profilOkusatToString(profilOkusa: ProfilOkusaBiljke) : String {
        return profilOkusa.toString()
    }
    @TypeConverter
    fun stringToProfilOkusa(value: String) : ProfilOkusaBiljke {
        return ProfilOkusaBiljke.valueOf(value)
    }

    @TypeConverter
    fun jelaToString(jela: List<String>) : String {
        return jela.joinToString (separator = ",")
    }
    @TypeConverter
    fun stringToJela(value: String) : List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun klimatskiTipoviToString(klimatskiTipovi: List<KlimatskiTip>) : String {
        return klimatskiTipovi.joinToString (separator = ",")
    }
    @TypeConverter
    fun stringToKlimatskiTip(value: String) : List<KlimatskiTip> {
        val values: List<String> = value.split(",").map { it.trim() }
        val klimatskiTipovi = mutableListOf<KlimatskiTip>()

        for (s in values) klimatskiTipovi.add(KlimatskiTip.valueOf(s))

        return klimatskiTipovi
    }

    @TypeConverter
    fun zemljisniTipoviToString(zemljisniTipovi: List<Zemljiste>) : String {
        return zemljisniTipovi.joinToString (separator = ",")
    }
    @TypeConverter
    fun stringToZemljisniTipovi(value: String) : List<Zemljiste> {
        val values: List<String> = value.split(",").map { it.trim() }
        val zemljisniTipovi = mutableListOf<Zemljiste>()

        for (s in values) zemljisniTipovi.add(Zemljiste.valueOf(s))

        return zemljisniTipovi
    }

    @TypeConverter
    fun bitmapToBase64(bitmap: Bitmap) : String{
        // create a ByteBuffer and allocate size equal to bytes in   the bitmap
        val byteBuffer = ByteBuffer.allocate(bitmap.height * bitmap.rowBytes)
        //copy all the pixels from bitmap to byteBuffer
        bitmap.copyPixelsToBuffer(byteBuffer)
        //convert byte buffer into byteArray
        val byteArray = byteBuffer.array()
        //convert byteArray to Base64 String with default flags
        return Base64.encodeToString(byteArray, DEFAULT)
    }

    @TypeConverter
    fun base64ToBitmap(base64String: String):Bitmap{
        //convert Base64 String into byteArray
        val byteArray = Base64.decode(base64String, DEFAULT)
        //byteArray to Bitmap
        return BitmapFactory.decodeByteArray(byteArray,
            0, byteArray.size)
    }
}