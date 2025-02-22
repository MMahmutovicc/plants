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

    private var plantsList = mutableListOf<Biljka>()
    private var images = mutableMapOf<Long,Bitmap>()
    private var flowerColor : String = "red"


    private lateinit var medicalListAdapter: MedicalListAdapter
    private lateinit var botanicalListAdapter: BotanicalListAdapter
    private lateinit var cookingListAdapter: CookingListAdapter

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
                    savePlant(newPlant)
                }
            }
        }
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
                if(biljkeRV.adapter == medicalListAdapter)
                    plantsList = medicalListAdapter.getPlants()

                else if(biljkeRV.adapter == botanicalListAdapter)
                    plantsList = botanicalListAdapter.getPlants()

                else
                    plantsList = cookingListAdapter.getPlants()

                if(position == 0) {
                    secondRow.visibility = View.GONE
                    biljkeRV.adapter = medicalListAdapter
                    medicalListAdapter.updatePlants(plantsList)
                }
                else if(position == 1) {
                    secondRow.visibility = View.GONE
                    biljkeRV.adapter = cookingListAdapter
                    cookingListAdapter.updatePlants(plantsList)
                }
                else if(position == 2) {
                    secondRow.visibility = View.VISIBLE
                    biljkeRV.adapter = botanicalListAdapter
                    botanicalListAdapter.updatePlants(plantsList)
                }
            }
        }
        brzaPretraga.setOnClickListener {
            if(pretragaET.text.isNotEmpty())
                getPlantsWithFlowerColor()
        }
        resetBtn.setOnClickListener {
            getAllPlants()
        }
        novaBiljkaBtn.setOnClickListener {
            secondRow.visibility = View.GONE
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            addPlant.launch(intent)
        }

        medicalListAdapter = MedicalListAdapter(plantsList, plantsList, images)

        botanicalListAdapter = BotanicalListAdapter(plantsList, plantsList, images)

        cookingListAdapter = CookingListAdapter(plantsList, plantsList, images)

        biljkeRV.adapter = medicalListAdapter
        setup()
        //clearDatabase()
    }
    private fun getAllImages() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var db = BiljkaDatabase.getInstance(applicationContext)
            for (plant in plantsList) {
                var idBiljke : Long? = plant.id
                if (idBiljke != null) {
                    var bitmap : Bitmap? = db.biljkaDao().getImage(idBiljke)?.bitmap
                    if (bitmap != null)
                        images[idBiljke] = bitmap
                    else {
                        val trefleDAO = TrefleDAO(applicationContext)
                        val newBitmap = trefleDAO.getImage(plant)
                        db.biljkaDao().addImage(idBiljke,newBitmap)
                        images[idBiljke] = newBitmap
                    }
                }
            }
            medicalListAdapter.updateAll(plantsList, images)
            cookingListAdapter.updateAll(plantsList, images)
            botanicalListAdapter.updateAll(plantsList, images)
        }
    }
    private fun setup() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var db = BiljkaDatabase.getInstance(applicationContext)
            plantsList = db.biljkaDao().getAllBiljkas().toMutableList()
            if (plantsList.isEmpty()) {
                //If there are no plants add starting plants
                val startingPlants = getPLants()
                for (plant in startingPlants) {
                    db.biljkaDao().saveBiljka(plant)
                }
                plantsList = db.biljkaDao().getAllBiljkas().toMutableList()
            }
            getAllImages()
        }
    }
    private fun getAllPlants() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var db = BiljkaDatabase.getInstance(applicationContext)
            plantsList = db.biljkaDao().getAllBiljkas().toMutableList()

            if(biljkeRV.adapter == medicalListAdapter) {
                medicalListAdapter.updateAll(plantsList,images)
            }
            else if(biljkeRV.adapter == botanicalListAdapter) {
                botanicalListAdapter.updatePlants(plantsList)
            }
            else {
                cookingListAdapter.updatePlants(plantsList)
            }
        }
    }

    private fun savePlant(newPlant: Biljka) {
        val toast = Toast.makeText(this, "Dodavanje biljke...", Toast.LENGTH_SHORT) // in Activity
        toast.show()
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var db = BiljkaDatabase.getInstance(applicationContext)
            var isAdded = db.biljkaDao().saveBiljka(newPlant)
            plantsList = db.biljkaDao().getAllBiljkas().toMutableList()
            currentAdapter = 0
            getAllImages()
            biljkeRV.adapter = medicalListAdapter
        }
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
            botanicalListAdapter.updatePlants(results.toMutableList(), 1,plantsImages)
        }
    }
    private fun clearDatabase() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var db = BiljkaDatabase.getInstance(applicationContext)
            db.biljkaDao().clearData()
        }
    }
}