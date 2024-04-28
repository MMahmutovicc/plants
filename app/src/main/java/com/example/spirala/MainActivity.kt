package com.example.spirala

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var modSpinner: Spinner
    private lateinit var resetBtn: Button
    private lateinit var novaBiljkaBtn: Button
    private var plantsList = getPLants()
    private var allPlants = getPLants()

    private lateinit var biljkeRV: RecyclerView

    private lateinit var medicalListAdapter: MedicalListAdapter
    private lateinit var botanicalListAdapter: BotanicalListAdapter
    private lateinit var cookingListAdapter: CookingListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        val addPlant = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val newPlant : Biljka? = data?.getParcelableExtra("newPlant")
                if (newPlant != null) {
                    plantsList.add(newPlant)
                    allPlants.add(newPlant)
                    medicalListAdapter.setAllPlants(allPlants)
                    botanicalListAdapter.setAllPlants(allPlants)
                    cookingListAdapter.setAllPlants(allPlants)
                    if(biljkeRV.adapter == medicalListAdapter)
                        medicalListAdapter.updatePlants(plantsList)
                    else if(biljkeRV.adapter == botanicalListAdapter)
                        botanicalListAdapter.updatePlants(plantsList)
                    else
                        cookingListAdapter.updatePlants(plantsList)
                    val toast = Toast.makeText(this, "Uspjesno ste dodali novu biljku", Toast.LENGTH_SHORT) // in Activity
                    toast.show()
                }
            }
        }

        modSpinner = findViewById(R.id.modSpinner)
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
        biljkeRV = findViewById(R.id.biljkeRV)
        biljkeRV.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
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
                    biljkeRV.adapter = medicalListAdapter
                    medicalListAdapter.updatePlants(plantsList)
                    medicalListAdapter.setAllPlants(allPlants)
                }
                else if(position == 1) {
                    biljkeRV.adapter = cookingListAdapter
                    cookingListAdapter.updatePlants(plantsList)
                    cookingListAdapter.setAllPlants(allPlants)
                }
                else if(position == 2) {
                    biljkeRV.adapter = botanicalListAdapter
                    botanicalListAdapter.updatePlants(plantsList)
                    botanicalListAdapter.setAllPlants(allPlants)
                }
            }
        }
        resetBtn.setOnClickListener {
            plantsList = allPlants
            if(biljkeRV.adapter == medicalListAdapter)
                medicalListAdapter.updatePlants(plantsList)
            else if(biljkeRV.adapter == botanicalListAdapter)
                botanicalListAdapter.updatePlants(plantsList)
            else
                cookingListAdapter.updatePlants(plantsList)
        }
        novaBiljkaBtn.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            addPlant.launch(intent)
        }
        medicalListAdapter = MedicalListAdapter(mutableListOf(), mutableListOf())
        biljkeRV.adapter = medicalListAdapter
        medicalListAdapter.updatePlants(plantsList)
        medicalListAdapter.setAllPlants(allPlants)

        botanicalListAdapter = BotanicalListAdapter(mutableListOf(),mutableListOf())

        cookingListAdapter = CookingListAdapter(mutableListOf(),mutableListOf())

    }
}