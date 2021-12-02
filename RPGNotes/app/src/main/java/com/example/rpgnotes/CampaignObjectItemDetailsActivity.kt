package com.example.rpgnotes

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.io.*





class CampaignObjectItemDetailsActivity : AppCompatActivity() {
    private lateinit var iv:ImageView
    private lateinit var editText:EditText
    private lateinit var editDesc:EditText
    private lateinit var editName:EditText

    companion object{
        const val uuid_key:String = "com.example.rpgnotes.uuid"
        const val type_key:String = "com.example.rpgnotes.type"
        const val index_key:String = "com.example.rpgnotes.index"
    }
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission() ) { isGranted: Boolean ->
        if(isGranted){
            requestGallery()
        }
    }
    val requestPermissionLauncherInternet = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requestItem()
        }
    }
    private val startForImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                iv.setImageURI(Uri.parse(intent?.data.toString()))
                //update data on change
                when(typeObj) {
                    "NPCs" -> {
                        CampaignData.getCampaignList()[uuid!!].NPCs!![index!!].images?.add(intent?.data.toString())
                        writeDataToFile(CampaignData)
                    }
                    "Cities" -> {
                        CampaignData.getCampaignList()[uuid!!].Cities!![index!!].images?.add(intent?.data.toString())
                        writeDataToFile(CampaignData)
                    }
                    "Places" -> {
                        CampaignData.getCampaignList()[uuid!!].Places!![index!!].images?.add(intent?.data.toString())
                        writeDataToFile(CampaignData)
                    }
                    "Quests" -> {
                        CampaignData.getCampaignList()[uuid!!].Quests!![index!!].images?.add(intent?.data.toString())
                        writeDataToFile(CampaignData)
                    }
                    "Items" -> {
                        CampaignData.getCampaignList()[uuid!!].Items!![index!!].images?.add(intent?.data.toString())
                        writeDataToFile(CampaignData)
                    }
                }

            }
        }

    private lateinit var item:CampaignObject
    private var uuid:Int? = null
    private var typeObj:String? = null
    private var index:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_object_item_details)
        //data
        uuid = intent.getStringExtra(uuid_key)?.toInt()
        typeObj = intent.getStringExtra(type_key)
        index = intent.getStringExtra(index_key)?.toInt()
        when(typeObj){
            "NPCs" -> item = CampaignData.getCampaignList()[uuid!!].NPCs!![index!!]
            "Cities" -> item = CampaignData.getCampaignList()[uuid!!].Cities!![index!!]
            "Places" -> item = CampaignData.getCampaignList()[uuid!!].Places!![index!!]
            "Quests" -> item = CampaignData.getCampaignList()[uuid!!].Quests!![index!!]
            "Items" -> item = CampaignData.getCampaignList()[uuid!!].Items!![index!!]
        }

        //layout
        val actionBar = supportActionBar
        actionBar!!.title = item.name
        var nameLbl = TextView(this).apply {
            setText(R.string.name_lbl)
            setTextColor(Color.parseColor("#FFFFFF"))
        }
        editName = EditText(this).apply {
            setText(item.name)
            setTextColor(Color.parseColor("#FFFFFF"))
            doAfterTextChanged {
                //update data on change
                when(typeObj){
                    "NPCs" -> {
                        CampaignData.getCampaignList()[uuid!!].NPCs!![index!!].name = it.toString()
                        writeDataToFile(CampaignData)
                        supportActionBar?.title = it.toString()
                    }
                    "Cities" -> {
                        CampaignData.getCampaignList()[uuid!!].Cities!![index!!].name = it.toString()
                        writeDataToFile(CampaignData)
                        supportActionBar?.title = it.toString()
                    }
                    "Places" -> {
                        CampaignData.getCampaignList()[uuid!!].Places!![index!!].name = it.toString()
                        writeDataToFile(CampaignData)
                        supportActionBar?.title = it.toString()
                    }
                    "Quests" -> {
                        CampaignData.getCampaignList()[uuid!!].Quests!![index!!].name = it.toString()
                        writeDataToFile(CampaignData)
                        supportActionBar?.title = it.toString()
                    }
                    "Items" -> {
                        CampaignData.getCampaignList()[uuid!!].Items!![index!!].name = it.toString()
                        writeDataToFile(CampaignData)
                        supportActionBar?.title = it.toString()
                    }
                }
            }
        }
        var descLbl = TextView(this).apply {
            setText(R.string.desc_lbl)
            setTextColor(Color.parseColor("#FFFFFF"))
        }
        editDesc = EditText(this).apply {
            setText(item.desc)
            setTextColor(Color.parseColor("#FFFFFF"))
            doAfterTextChanged {
                //update data on change
                when(typeObj){
                    "NPCs" -> {
                        CampaignData.getCampaignList()[uuid!!].NPCs!![index!!].desc = it.toString()
                        writeDataToFile(CampaignData)
                    }
                    "Cities" -> {
                        CampaignData.getCampaignList()[uuid!!].Cities!![index!!].desc = it.toString()
                        writeDataToFile(CampaignData)
                    }
                    "Places" -> {
                        CampaignData.getCampaignList()[uuid!!].Places!![index!!].desc = it.toString()
                        writeDataToFile(CampaignData)
                    }
                    "Quests" -> {
                        CampaignData.getCampaignList()[uuid!!].Quests!![index!!].desc = it.toString()
                        writeDataToFile(CampaignData)
                    }
                    "Items" -> {
                        CampaignData.getCampaignList()[uuid!!].Items!![index!!].desc = it.toString()
                        writeDataToFile(CampaignData)
                    }
                }
            }
        }
        // listview for notes?
        //create image stuff
        //make image view
        iv = ImageView(this).apply{
            if(item.images?.isNotEmpty() == true){
                setImageURI(Uri.parse(item.images?.get(0).toString()))
            }
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        var add = Button(this).apply {
            setBackgroundResource(R.drawable.ic_camera_foreground)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        var lbl = TextView(this).apply {
            text = context.getString(R.string.item_search_lbl)
            setTextColor(Color.parseColor("#FFFFFF"))
            gravity = Gravity.CENTER
        }
        editText = EditText(this).apply {
            gravity = Gravity.CENTER
            hint = "ex: GreatAxe"
            setHintTextColor(Color.LTGRAY)
            setTextColor(Color.parseColor("#FFFFFF"))
            setText("")
        }
        var searchBtn = Button(this).apply {
            setTextColor(Color.parseColor("#FFFFFF"))
            setBackgroundResource(R.drawable.border_button)
            text = "Search Item"
            setOnClickListener {
                if(editText.text.toString() == ""){
                    Toast.makeText(this@CampaignObjectItemDetailsActivity, "Please Enter A Value First", Toast.LENGTH_LONG).show()
                } else {
                    requestPermissionLauncherInternet.launch(Manifest.permission.INTERNET)
                }
            }
        }
        var ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#282828"))
            addView(nameLbl)
            addView(editName)
            addView(descLbl)
            addView(editDesc)
            addView(iv)
            addView(add)
            if (typeObj == "Items") {
                addView(lbl)
                addView(editText)
                addView(searchBtn)
            }
        }
        findViewById<ConstraintLayout>(R.id.campaign_object_item_detail_layout).addView(ll)
        findViewById<ConstraintLayout>(R.id.campaign_object_item_detail_layout).setBackgroundColor(Color.parseColor("#282828"))
    }

    override fun onResume() {
        super.onResume()
        val jsonResult = readDataAsJSON()
        if(jsonResult != null){
            CampaignData.loadCampaign(jsonResult)
        }
    }

    override fun onPause() {
        super.onPause()
        writeDataToFile(CampaignData)
    }
    private fun writeDataToFile(campaignData: CampaignData) {
        val file:String = "campaigns.json"
        val data:String = CampaignData.toJSON().toString()
        try {
            val fileOutputStream: FileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun readDataAsJSON(): JSONArray?{
        try {
            var fileInputStream: FileInputStream? = openFileInput("campaigns.json")
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while (run {
                    text = bufferedReader.readLine();
                    text
                } != null) {
                stringBuilder.append(text)
            }
            fileInputStream?.close()
            return JSONArray(stringBuilder.toString())
        } catch (e: FileNotFoundException) {
            return null
        }
    }
    private fun requestGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startForImage.launch(intent)
    }
    // the user opens the menu for the first time
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.campaign_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete-> {
                when(typeObj) {
                    "NPCs" -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Are you sure you want to Delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                // Delete
                                CampaignData.getCampaignList()[uuid!!].NPCs!!.removeAt(index!!)
                                var player = MediaPlayer.create(this@CampaignObjectItemDetailsActivity,R.raw.crumple)
                                player!!.start()
                                writeDataToFile(CampaignData)
                                finish()
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                    }
                    "Cities" -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Are you sure you want to Delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                // Delete
                                CampaignData.getCampaignList()[uuid!!].Cities!!.removeAt(index!!)
                                var player = MediaPlayer.create(this@CampaignObjectItemDetailsActivity,R.raw.crumple)
                                player!!.start()
                                writeDataToFile(CampaignData)
                                finish()
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                    }
                    "Places" -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Are you sure you want to Delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                // Delete
                                CampaignData.getCampaignList()[uuid!!].Places!!.removeAt(index!!)
                                var player = MediaPlayer.create(this@CampaignObjectItemDetailsActivity,R.raw.crumple)
                                player!!.start()
                                writeDataToFile(CampaignData)
                                finish()
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                    }
                    "Quests" -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Are you sure you want to Delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                // Delete
                                CampaignData.getCampaignList()[uuid!!].Quests!!.removeAt(index!!)
                                var player = MediaPlayer.create(this@CampaignObjectItemDetailsActivity,R.raw.crumple)
                                player!!.start()
                                writeDataToFile(CampaignData)
                                finish()
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                    }
                    "Items" -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Are you sure you want to Delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                // Delete
                                CampaignData.getCampaignList()[uuid!!].Items!!.removeAt(index!!)
                                var player = MediaPlayer.create(this@CampaignObjectItemDetailsActivity,R.raw.crumple)
                                player!!.start()
                                writeDataToFile(CampaignData)
                                finish()
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("SetTextI18n")
    private fun requestItem() {
        //https://www.dnd5eapi.co/api/equipment
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
                    editName.setText(jResponse.getString("name"))
                    var str = JSONObject(jResponse.getString("equipment_category")).getString("name")
                    str += " "+JSONObject(jResponse.getString("cost")).getInt("quantity").toString()
                    str += " "+ JSONObject(jResponse.getString("cost")).getString("unit")
                    editDesc.setText(str)
                }

            },
            { Toast.makeText(this, "No Matching entries found", Toast.LENGTH_SHORT).show()})
        queue.add(stringRequest)
    }
}