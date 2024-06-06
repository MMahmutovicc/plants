package com.example.spirala

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class TrefleDAO {
    private val trefle_token = BuildConfig.TREFLE_TOKEN
    //private val defaultBitmap : Bitmap = BitmapFactory.decodeStream(URL("https://d2jx2rerrg6sh3.cloudfront.net/image-handler/ts/20231010081754/ri/950/src/images/news/ImageForNews_761308_16969834687268837.jpg").openConnection().getInputStream())
    //https://bs.plantnet.org/image/o/73507975ac242e85f08b73e35452602d0d21a477
    //https://d2jx2rerrg6sh3.cloudfront.net/image-handler/ts/20231010081754/ri/950/src/images/news/ImageForNews_761308_16969834687268837.jpg
    private lateinit var defaultBitmap : Bitmap
    private val soils = mapOf(
        1 to Zemljiste.GLINENO,
        2 to Zemljiste.GLINENO,
        3 to Zemljiste.PJESKOVITO,
        4 to Zemljiste.PJESKOVITO,
        5 to Zemljiste.ILOVACA,
        6 to Zemljiste.ILOVACA,
        7 to Zemljiste.CRNICA,
        8 to Zemljiste.CRNICA,
        9 to Zemljiste.SLJUNOVITO,
        10 to Zemljiste.KRECNJACKO
    )

    private val climates = mutableMapOf(
        KlimatskiTip.SREDOZEMNA to listOf(Pair(6,9),Pair(1,5)),
        KlimatskiTip.TROPSKA to listOf(Pair(8,10),Pair(7,10)),
        KlimatskiTip.SUBTROPSKA to listOf(Pair(6,9),Pair(5,8)),
        KlimatskiTip.UMJERENA to listOf(Pair(4,7),Pair(3,7)),
        KlimatskiTip.SUHA to listOf(Pair(7,9),Pair(1,2)),
        KlimatskiTip.PLANINSKA to listOf(Pair(0,5),Pair(3,7)),
    )
    constructor(context: Context) {
        this.defaultBitmap = BitmapFactory.decodeResource(context.resources , R.drawable.picture)
    }
    constructor()
    suspend fun getImage(biljka: Biljka) : Bitmap {
        return withContext(Dispatchers.IO) {
            var imageBitmap = defaultBitmap
            val latinName : String = biljka.naziv.substringAfter("(").substringBefore(")")
            val plantsBody = ApiAdapter.retrofit.getPlants(latinName).body()
            if(plantsBody != null) {
                val plants = plantsBody.plants
                val newImageBitmap = BitmapFactory.decodeStream(URL(plants[0].imageUrl).openConnection().getInputStream())
                if(newImageBitmap != null)
                    imageBitmap = newImageBitmap
            }
            return@withContext imageBitmap
        }
    }
    suspend fun fixData(biljka: Biljka) : Biljka {
        return withContext(Dispatchers.IO) {
            var latinName : String = biljka.naziv.substringAfter("(").substringBefore(")")
            val plantsBody = ApiAdapter.retrofit.getPlants(latinName).body()
            if (plantsBody != null) {
                val plants = plantsBody.plants
                val id = plants[0].id
                val plantBody = ApiAdapter.retrofit.getPlant(id).body()
                if (plantBody != null) {
                    val plant = plantBody.plant
                    if (biljka.porodica != plant.family) {
                        biljka.porodica = plant.family
                    }
                    if (!plant.edible) {
                        if (!biljka.medicinskoUpozorenje.contains("NIJE JESTIVO")) {
                            biljka.medicinskoUpozorenje += " NIJE JESTIVO"
                        }
                        biljka.jela = emptyList()
                    }
                    if (plant.specifications.toxicity != null && plant.specifications.toxicity != "none") {
                        if(!biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                            biljka.medicinskoUpozorenje += " TOKSIČNO"
                        }
                    }
                    if(plant.growth.soilTexture != null) {
                        val newTypes = mutableListOf<Zemljiste>()
                        //val numbers = Regex("\\d+").findAll(soilTexture).map { it.value }.toList()
                        //for(number in numbers) {}
                        if (soils.containsKey(plant.growth.soilTexture)) {
                            newTypes.add(soils.getValue(plant.growth.soilTexture))
                        }
                        biljka.zemljisniTipovi = newTypes
                    }
                    if(plant.growth.light != null && plant.growth.atmosphericHumidity != null) {
                        val newTypes = mutableListOf<KlimatskiTip>()
                        for (entry in climates.entries.iterator()) {
                            if (entry.value[0].first <= plant.growth.light && plant.growth.light <= entry.value[0].second &&
                                entry.value[1].first <= plant.growth.atmosphericHumidity && plant.growth.atmosphericHumidity <= entry.value[1].second)
                                newTypes.add(entry.key)
                        }
                        biljka.klimatskiTipovi = newTypes
                    }
                }
            }
            return@withContext biljka
        }
    }
    suspend fun getPlantsWithFlowerColor(flower_color:String, substr: String): List<Biljka> {
        return withContext(Dispatchers.IO) {
            val biljke = mutableListOf<Biljka>()
            val plantsBody = ApiAdapter.retrofit.getPlantsByFlowerColor(substr, flower_color).body()
            if(plantsBody != null) {
                val plants = plantsBody.plants
                for (eachPlant in plants) {
                    val id = eachPlant.id
                    val plantBody = ApiAdapter.retrofit.getPlant(id).body()
                    if (plantBody != null) {
                        val plant = plantBody.plant
                        val naziv = plant.name + " ("+plant.latName+")"
                        val porodica = plant.family
                        val medicinskoUpozorenje = ""
                        val medicinskeKoristi = listOf<MedicinskaKorist>()
                        val profilOkusaBiljke = ProfilOkusaBiljke.GORKO
                        val jela = emptyList<String>()
                        val klimatskiTipovi = mutableListOf<KlimatskiTip>()
                        val zemljisniTipovi = mutableListOf<Zemljiste>()
                        if(plant.growth.soilTexture != null && soils.containsKey(plant.growth.soilTexture))
                            zemljisniTipovi.add(soils.getValue(plant.growth.soilTexture))
                        if(plant.growth.light != null && plant.growth.atmosphericHumidity != null) {
                            for (entry in climates.entries.iterator()) {
                                if (entry.value[0].first <= plant.growth.light && plant.growth.light <= entry.value[0].second &&
                                    entry.value[1].first <= plant.growth.atmosphericHumidity && plant.growth.atmosphericHumidity <= entry.value[1].second)
                                    klimatskiTipovi.add(entry.key)
                            }
                        }
                        biljke.add(Biljka(naziv, porodica, medicinskoUpozorenje, medicinskeKoristi, profilOkusaBiljke, jela, klimatskiTipovi, zemljisniTipovi))
                    }
                }
            }
            return@withContext biljke
        }
    }
}