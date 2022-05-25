package com.example.listviewesharedpreferences_stackoverflow

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    /* Variáveis de Escopo Gloval: */
    private lateinit var listView: ListView
    private lateinit var editTextName: EditText
    private lateinit var editTextNumber: EditText
    private lateinit var buttonAdd: Button
    private var dataArrayList: ArrayList<String>? = null
    private var adapter: ArrayAdapter<String>? = null
    // private var prefe1: SharedPreferences? = null
    private lateinit var prefe1: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Inicializar Variaveis(): */
        declararVariaveis()
        declararFuncoes()
        addInformations()
        deleteInformation()
    }

    private fun declararVariaveis(){

        dataArrayList = java.util.ArrayList()
        leerSharedPreferences()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataArrayList!!)
        listView = findViewById<View>(R.id.listVw_actvMain) as ListView
        listView.adapter = adapter
        editTextName = findViewById<View>(R.id.edtTxt_addName_actvtMain) as EditText
        editTextNumber = findViewById<View>(R.id.edtTxt_addNumber_actvtMain) as EditText
        buttonAdd = findViewById(R.id.bttn_add_actvtMain)
    }

    private fun declararFuncoes(){

        buttonAdd.setOnClickListener { addInformations() }

    }

    private fun addInformations() {

        dataArrayList!!.add(editTextName.text.toString() + " : " + editTextNumber.text.toString())
        adapter!!.notifyDataSetChanged()
        val elemento = prefe1.edit()
        elemento.putString(editTextName.text.toString(), editTextNumber.text.toString())
        elemento.apply()
        editTextName.setText("")
        editTextNumber.setText("")

    }

    private fun deleteInformation(){

        listView.onItemLongClickListener =
            OnItemLongClickListener { adapterView, view, i, l ->
                val dialogo1 = AlertDialog.Builder(this@MainActivity)
                dialogo1.setTitle("Importante")
                dialogo1.setMessage("¿ Elimina este teléfono ?")
                dialogo1.setCancelable(false)
                dialogo1.setPositiveButton(
                    "Confirmar"
                ) { dialogo1, id ->
                    val s = dataArrayList!![i]
                    val tok1 = StringTokenizer(s, ":")
                    val nom = tok1.nextToken().trim { it <= ' ' }
                    val elemento = prefe1!!.edit()
                    elemento.remove(nom)
                    elemento.commit()
                    dataArrayList!!.removeAt(i)
                    adapter!!.notifyDataSetChanged()
                }
                dialogo1.setNegativeButton(
                    "Cancelar"
                ) { dialogo1, id -> }
                dialogo1.show()
                false
            }
    }

    private fun leerSharedPreferences() {

        prefe1 = getSharedPreferences("datostelefonos", MODE_PRIVATE)
        val claves = prefe1.all
        for ((key, value) in claves) {
            dataArrayList!!.add(key + " : " + value.toString())

        }
    }
}
