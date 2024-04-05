package com.example.spirala

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var modSpinner: Spinner
    private lateinit var resetBtn: Button
    private var plantsList = getPLants()

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
        modSpinner = findViewById(R.id.modSpinner)
        resetBtn = findViewById(R.id.resetBtn);
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
                }
                else if(position == 1) {
                    biljkeRV.adapter = cookingListAdapter
                    cookingListAdapter.updatePlants(plantsList)
                }
                else if(position == 2) {
                    biljkeRV.adapter = botanicalListAdapter
                    botanicalListAdapter.updatePlants(plantsList)
                }
            }
        }
        resetBtn.setOnClickListener {
            plantsList = getPLants()
            if(biljkeRV.adapter == medicalListAdapter)
                medicalListAdapter.updatePlants(plantsList)
            else if(biljkeRV.adapter == botanicalListAdapter)
                botanicalListAdapter.updatePlants(plantsList)
            else
                cookingListAdapter.updatePlants(plantsList)
        }
        medicalListAdapter = MedicalListAdapter(listOf())
        biljkeRV.adapter = medicalListAdapter
        medicalListAdapter.updatePlants(plantsList)

        botanicalListAdapter = BotanicalListAdapter(listOf())
        botanicalListAdapter.updatePlants(plantsList)

        cookingListAdapter = CookingListAdapter(listOf())
        cookingListAdapter.updatePlants(plantsList)
    }

}