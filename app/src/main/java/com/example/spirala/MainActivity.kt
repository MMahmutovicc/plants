package com.example.spirala

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var modSpinner: Spinner
    private lateinit var bojaSPIN: Spinner
    private lateinit var resetBtn: Button
    private lateinit var novaBiljkaBtn: Button
    private lateinit var pretragaET: EditText
    private lateinit var biljkeRV: RecyclerView
    private lateinit var brzaPretraga: Button
    private lateinit var secondRow: ConstraintLayout

    private var plantsList = getPLants()
    private var allPlants = getPLants()
    private var images = mutableListOf<Bitmap>()
    private var allImages = mutableListOf<Bitmap>()
    private var flowerColor : String = "red"


    private lateinit var medicalListAdapter: MedicalListAdapter
    private lateinit var botanicalListAdapter: BotanicalListAdapter
    private lateinit var cookingListAdapter: CookingListAdapter

    private lateinit var getPlantsFromAdapter : List<MutableList<Biljka>>
    private lateinit var getImagesFromAdapter : List<MutableList<Biljka>>
    private var currentAdapter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Rezultat dodavanja nove biljke
        val addPlant = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val newPlant : Biljka? = data?.getParcelableExtra("newPlant")
                if (newPlant != null) {
                    getImage(newPlant)
                    plantsList.add(newPlant)
                    allPlants.add(newPlant)
                    medicalListAdapter.setAllPlants(allPlants, allImages)
                    botanicalListAdapter.setAllPlants(allPlants, allImages)
                    cookingListAdapter.setAllPlants(allPlants, allImages)
                    if(biljkeRV.adapter == medicalListAdapter)
                        medicalListAdapter.updatePlants(plantsList, images)
                    else if(biljkeRV.adapter == botanicalListAdapter)
                        botanicalListAdapter.updatePlants(plantsList, images)
                    else
                        cookingListAdapter.updatePlants(plantsList, images)
                    val toast = Toast.makeText(this, "Uspješno ste dodali novu biljku", Toast.LENGTH_SHORT) // in Activity
                    toast.show()
                }
            }
        }
        getImages()
        Log.d("slike", "${allImages.size}")
        secondRow = findViewById(R.id.secondRow)
        modSpinner = findViewById(R.id.modSpinner)
        bojaSPIN = findViewById(R.id.bojaSPIN)
        pretragaET = findViewById(R.id.pretragaET)
        brzaPretraga = findViewById(R.id.brzaPretraga)
        resetBtn = findViewById(R.id.resetBtn)
        novaBiljkaBtn = findViewById(R.id.novaBiljkaBtn)
        ArrayAdapter.createFromResource(
            this,
            R.array.modSpinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            modSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.bojaSPIN,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bojaSPIN.adapter = adapter
        }
        biljkeRV = findViewById(R.id.biljkeRV)
        biljkeRV.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        bojaSPIN.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                flowerColor = parent?.getItemAtPosition(position).toString()
            }
        }
        modSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(biljkeRV.adapter == medicalListAdapter) {
                    plantsList = medicalListAdapter.getPlants()
                    images = medicalListAdapter.getImages()
                }
                else if(biljkeRV.adapter == botanicalListAdapter) {
                    plantsList = botanicalListAdapter.getPlants()
                    images = botanicalListAdapter.getImages()
                }
                else {
                    plantsList = cookingListAdapter.getPlants()
                    images = cookingListAdapter.getImages()
                }
                if(position == 0) {
                    secondRow.visibility = View.GONE
                    biljkeRV.adapter = medicalListAdapter
                    medicalListAdapter.updatePlants(plantsList, images)
                    medicalListAdapter.setAllPlants(allPlants, allImages)
                }
                else if(position == 1) {
                    secondRow.visibility = View.GONE
                    biljkeRV.adapter = cookingListAdapter
                    cookingListAdapter.updatePlants(plantsList, images)
                    cookingListAdapter.setAllPlants(allPlants, allImages)
                }
                else if(position == 2) {
                    secondRow.visibility = View.VISIBLE
                    biljkeRV.adapter = botanicalListAdapter
                    botanicalListAdapter.updatePlants(plantsList, images)
                    botanicalListAdapter.setAllPlants(allPlants, allImages)
                }
            }
        }
        brzaPretraga.setOnClickListener {
            if(pretragaET.text.isNotEmpty())
                getPlantsWithFlowerColor()
        }
        resetBtn.setOnClickListener {
            plantsList = allPlants
            images = allImages
            if(biljkeRV.adapter == medicalListAdapter) {
                medicalListAdapter.updatePlants(plantsList, images)
            }
            else if(biljkeRV.adapter == botanicalListAdapter) {
                botanicalListAdapter.updatePlants(plantsList, images)
            }
            else {
                cookingListAdapter.updatePlants(plantsList, images)
            }
        }
        novaBiljkaBtn.setOnClickListener {
            secondRow.visibility = View.GONE
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            addPlant.launch(intent)
        }
        medicalListAdapter = MedicalListAdapter(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
        biljkeRV.adapter = medicalListAdapter
        medicalListAdapter.updatePlants(plantsList, images)
        medicalListAdapter.setAllPlants(allPlants, allImages)

        botanicalListAdapter = BotanicalListAdapter(mutableListOf(),mutableListOf(), mutableListOf(),mutableListOf())

        cookingListAdapter = CookingListAdapter(mutableListOf(),mutableListOf(), mutableListOf(),mutableListOf())
    }

    private fun getPlantsWithFlowerColor() {
        val toast = Toast.makeText(this, "Pretraživanje...", Toast.LENGTH_SHORT) // in Activity
        toast.show()
        val substr = pretragaET.text.toString()
        val trefleDAO = TrefleDAO(applicationContext)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val results = trefleDAO.getPlantsWithFlowerColor(flowerColor, substr)
            val plantsImages = mutableListOf<Bitmap>()
            for (plant in results) {
                val imageResult = trefleDAO.getImage(plant)
                plantsImages.add(imageResult)
            }
            Log.d("poziv", "biljke="+results.size.toString()+" slike="+plantsImages.size.toString())
            botanicalListAdapter.updatePlants(results.toMutableList(), plantsImages, 1)
        }
    }

    private fun getImage(newPlant: Biljka) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        val trefleDAO = TrefleDAO(applicationContext)
        scope.launch {
                val result = trefleDAO.getImage(newPlant)
                allImages.add(result)
                images.add(result)
        }
    }

    private fun getImages() {
        Log.d("slike", "pozvana")
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        val trefleDAO = TrefleDAO(applicationContext)
        val startingPlants = allPlants
        scope.launch {
            for (plant in startingPlants) {
                val result = trefleDAO.getImage(plant)
                allImages.add(result)
                images.add(result)
                Log.d("slike", "${allImages.size}")
            }
            medicalListAdapter.updatePlants(plantsList, images)
            cookingListAdapter.updatePlants(plantsList, images)
            botanicalListAdapter.updatePlants(plantsList, images)
        }
    }
}