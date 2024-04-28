package com.example.spirala

import android.R
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class NovaBiljkaActivity : AppCompatActivity() {
    private var medicinskeKoristi : MutableList<MedicinskaKorist> = mutableListOf()
    private var klimatskiTipovi : MutableList<KlimatskiTip> = mutableListOf()
    private var zemljisniTipovi : MutableList<Zemljiste> = mutableListOf()
    private var profiliOkusa : MutableList<ProfilOkusaBiljke> = mutableListOf()
    private var jela : MutableList<String> = mutableListOf()
    private var izmijeni = -1

    private lateinit var nazivET : EditText
    private lateinit var porodicaET : EditText
    private lateinit var medicinskoUpozorenjeET : EditText
    private lateinit var jeloET : EditText
    private lateinit var medicinskaKoristLV : ListView
    private lateinit var klimatskiTipLV : ListView
    private lateinit var zemljisniTipLV : ListView
    private lateinit var profilOkusaLV : ListView
    private lateinit var jelaLV : ListView
    private lateinit var dodajJeloBtn: Button
    private lateinit var dodajBiljkuBtn: Button
    private lateinit var uslikajBiljkuBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_nova_biljka)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        nazivET = findViewById(R.id.nazivET)
        porodicaET = findViewById(R.id.porodicaET)
        medicinskoUpozorenjeET = findViewById(R.id.medicinskoUpozorenjeET)
        jeloET = findViewById(R.id.jeloET)
        medicinskaKoristLV = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipLV = findViewById(R.id.klimatskiTipLV)
        zemljisniTipLV = findViewById(R.id.zemljisniTipLV)
        profilOkusaLV = findViewById(R.id.profilOkusaLV)
        jelaLV = findViewById(R.id.jelaLV)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        dodajBiljkuBtn = findViewById(R.id.dodajBiljkuBtn)
        uslikajBiljkuBtn = findViewById(R.id.uslikajBiljkuBtn)
        //MEDICINSKA KORIST
        val medicinskaKoristAdapter: ArrayAdapter<*>
        medicinskaKoristAdapter = ArrayAdapter(this, R.layout.simple_list_item_1,MedicinskaKorist.entries.map { it.opis })
        //medicinskaKoristLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        medicinskaKoristLV.adapter = medicinskaKoristAdapter

        medicinskaKoristLV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = MedicinskaKorist.entries[position]
            if (medicinskeKoristi.contains(item))
                medicinskeKoristi.remove(item)
            else
                medicinskeKoristi.add(item)
        }

        //KLIMATSKI TIP
        val klimatskiTipAdapter: ArrayAdapter<*>
        klimatskiTipAdapter = ArrayAdapter(this, R.layout.simple_list_item_1,KlimatskiTip.entries.map { it.opis })
        klimatskiTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        klimatskiTipLV.adapter = klimatskiTipAdapter
        klimatskiTipLV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = KlimatskiTip.entries[position]
            if (klimatskiTipovi.contains(item))
                klimatskiTipovi.remove(item)
            else
                klimatskiTipovi.add(item)
        }
        //ZEMLJISNI TIP
        val zemljisniTipAdapter: ArrayAdapter<*>
        zemljisniTipAdapter = ArrayAdapter(this, R.layout.simple_list_item_1,Zemljiste.entries.map { it.naziv })
        zemljisniTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        zemljisniTipLV.adapter = zemljisniTipAdapter
        zemljisniTipLV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = Zemljiste.entries[position]
            if (zemljisniTipovi.contains(item))
                zemljisniTipovi.remove(item)
            else
                zemljisniTipovi.add(item)
        }
        //PROFIL OKUSA
        val profilOkusaAdapter: ArrayAdapter<*>
        profilOkusaAdapter = ArrayAdapter(this, R.layout.simple_list_item_1,ProfilOkusaBiljke.entries.map { it.opis })
        profilOkusaLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        profilOkusaLV.adapter = profilOkusaAdapter
        profilOkusaLV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = ProfilOkusaBiljke.entries[position]
            if (profiliOkusa.contains(item))
                profiliOkusa.remove(item)
            else
                profiliOkusa.add(item)
        }
        //JELO
        val jeloAdapter: ArrayAdapter<*>
        jeloAdapter = ArrayAdapter(this, R.layout.simple_list_item_1,jela)
        jelaLV.adapter = jeloAdapter
        jelaLV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = jela[position]
            jeloET.setText(item)
            dodajJeloBtn.text = "Izmijeni jelo"
            izmijeni = position
        }
        dodajJeloBtn.setOnClickListener {
            if(dodajJeloBtn.text.toString() == "Izmijeni jelo") {
                val filtered = jela.filterIndexed { index, _ -> index != izmijeni  }
                if(jeloET.text.length < 2 || jeloET.text.length > 20)
                    jeloET.error = "Neispravna duzina"
                else if (filtered.any { it.equals(jeloET.text.toString(), true) }) {
                    jeloET.error = "Jelo vec spremljeno"
                }
                else {
                    jela[izmijeni] = jeloET.text.toString()
                    izmijeni = -1
                    dodajJeloBtn.text = "Dodaj jelo"
                    jeloET.setText("")
                    jeloAdapter.notifyDataSetChanged()
                }
            }
            else {
                if(jeloET.text.length < 2 || jeloET.text.length > 20)
                    jeloET.error = "Neispravna duzina"
                else if (jela.any { it.equals(jeloET.text.toString(), true) }) {
                    jeloET.error = "Jelo vec spremljeno"
                }
                else {
                    jela.add(jeloET.text.toString())
                    jeloAdapter.notifyDataSetChanged()
                }
            }
        }
        //DODAJ BILJKU
        dodajBiljkuBtn.setOnClickListener {
            if(validacijaPolja()) {
                val biljka = Biljka(
                    nazivET.text.toString(),
                    porodicaET.text.toString(),
                    medicinskoUpozorenjeET.text.toString(),
                    medicinskeKoristi,
                    profiliOkusa.first(),
                    jela,
                    klimatskiTipovi,
                    zemljisniTipovi
                )
                val resultIntent = Intent()
                resultIntent.putExtra("newPlant",biljka)
                setResult(RESULT_OK,resultIntent)
                finish()
            }
        }
        //USLIKAJ BILJKU
        uslikajBiljkuBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent,1)
            //camera.launch(cameraIntent)
        }
    }
    private fun validacijaPolja() : Boolean {
        var t = true
        if(nazivET.text.length < 2 || nazivET.text.length > 20) {
            nazivET.error = "Neispravna duzina"
            t = false
        }
        if(porodicaET.text.length < 2 || porodicaET.text.length > 20) {
            porodicaET.error = "Neispravna duzina"
            t = false
        }
        if(medicinskoUpozorenjeET.text.length < 2 || medicinskoUpozorenjeET.text.length > 20) {
            medicinskoUpozorenjeET.error = "Neispravna duzina"
            t = false
        }
        if (medicinskeKoristi.isEmpty()) {
            val toast = Toast.makeText(this, "text", Toast.LENGTH_SHORT) // in Activity
            toast.show()
            t = false
        }
        if (klimatskiTipovi.isEmpty()) {
            val toast = Toast.makeText(this, "text", Toast.LENGTH_SHORT) // in Activity
            toast.show()
            t = false
        }
        if (zemljisniTipovi.isEmpty()) {
            val toast = Toast.makeText(this, "text", Toast.LENGTH_SHORT) // in Activity
            toast.show()
            t = false
        }
        if (jela.isEmpty()) {
            jeloET.error = "Niste dodali nijedno jelo"
            t = false
        }
        if (profiliOkusa.isEmpty()) {
            val toast = Toast.makeText(this, "text", Toast.LENGTH_SHORT) // in Activity
            toast.show()
            t = false
        }
        return t
    }
}