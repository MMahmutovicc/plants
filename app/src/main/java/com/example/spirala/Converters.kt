package com.example.spirala

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


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
    fun bitmapToBase64(bitmap: Bitmap) : String {
        val width = if (bitmap.width > 500) 500 else bitmap.width
        val height = if (bitmap.height > 660) 660 else bitmap.height
        val resizedBmp: Bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height)

        val outputStream = ByteArrayOutputStream()
        resizedBmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    @TypeConverter
    fun base64ToBitmap(string: String):Bitmap?{
        return try {
            val encodeByte = Base64.decode(string, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            null
        }
    }
}