package com.example.spirala

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Biljka(
    var naziv: String,
    var porodica: String,
    var medicinskoUpozorenje: String,
    var medicinskeKoristi: List<MedicinskaKorist>,
    var profilOkusa: ProfilOkusaBiljke,
    var jela: List<String>,
    var klimatskiTipovi: List<KlimatskiTip>,
    var zemljisniTipovi: List<Zemljiste>
) : Parcelable
