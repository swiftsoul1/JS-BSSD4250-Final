package com.example.rpgnotes

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

class APIActivity : AppCompatActivity() {
    private lateinit var editText:EditText
    private lateinit var nameView:TextView
    private lateinit var cateView:TextView
    private lateinit var goldView:TextView
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requestItem()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiactivity)
        var lbl = TextView(this).apply {
            text = context.getString(R.string.item_search_lbl)
            setTextColor(Color.parseColor("#FFFFFF"))
            gravity = Gravity.CENTER
        }
        editText = EditText(this).apply {
            gravity = Gravity.CENTER
            hint = "ex: GreatAxe"
            setHintTextColor(Color.parseColor("#FFFFFF"))
            setTextColor(Color.parseColor("#FFFFFF"))
            setText("")
        }
        var searchBtn = Button(this).apply {
            setTextColor(Color.parseColor("#FFFFFF"))
            setBackgroundResource(R.drawable.border_button)
            text = "Search Item"
            setOnClickListener {
                if(editText.text.toString() == ""){
                    Toast.makeText(this@APIActivity, "Please Enter A Value First", Toast.LENGTH_LONG).show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.INTERNET)
                }
            }
        }
        nameView = TextView(this).apply{
            setTextColor(Color.parseColor("#FFFFFF"))
            gravity = Gravity.CENTER
        }
        cateView = TextView(this).apply{
            setTextColor(Color.parseColor("#FFFFFF"))
            gravity = Gravity.CENTER
        }
        goldView = TextView(this).apply{
            setTextColor(Color.parseColor("#FFFFFF"))
            gravity = Gravity.CENTER
        }
        var ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#282828"))
            addView(lbl)
            addView(editText)
            addView(searchBtn)
            addView(nameView)
            addView(cateView)
            addView(goldView)
        }
        findViewById<ConstraintLayout>(R.id.api_layout).addView(ll)
    }
    @SuppressLint("SetTextI18n")
    private fun requestItem() {
        //https://www.dnd5eapi.co/api/equipment
        nameView.text = ""
        cateView.text = ""
        goldView.text = ""
        val queue = Volley.newRequestQueue(this)
        var url = "https://www.dnd5eapi.co/api/equipment/"
        url += editText.text.toString().lowercase()
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                var jResponse = JSONObject(response.toString())
                if(jResponse.has("error")) {
                    Toast.makeText(this, "No Matching entries found", Toast.LENGTH_SHORT).show()
                } else if(jResponse.has("name")){
                    nameView.text = jResponse.getString("name")
                    cateView.text = JSONObject(jResponse.getString("equipment_category")).getString("name")
                    goldView.text = JSONObject(jResponse.getString("cost")).getInt("quantity").toString()
                    var temp = goldView.text.toString()
                    goldView.text =  temp + " "+ JSONObject(jResponse.getString("cost")).getString("unit")
                }

            },
            { Toast.makeText(this, "No Matching entries found", Toast.LENGTH_SHORT).show()})
        queue.add(stringRequest)
    }
}