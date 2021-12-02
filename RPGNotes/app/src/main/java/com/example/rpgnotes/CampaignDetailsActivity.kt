package com.example.rpgnotes

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.commit
import org.json.JSONArray
import java.io.*

class CampaignDetailsActivity : AppCompatActivity() {
    private var fid = 0
    companion object{
        const val uuid_key:String = "com.example.rpgnotes.uuid"
    }
    private var uuid:Int? = null
    private lateinit var campaign:Campaign
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_details)

        //get data for selected campaign
        uuid = intent.getStringExtra(uuid_key)?.toInt()
        supportActionBar?.title = CampaignData.getCampaignList()[uuid!!].name
        //make layout
        var nameLbl = TextView(this).apply {
            setText(R.string.c_name_lbl)
            setTextColor(Color.parseColor("#FFFFFF"))
        }
        var editName = EditText(this).apply {
            setText(CampaignData.getCampaignList()[uuid!!].name)
            setTextColor(Color.parseColor("#FFFFFF"))
            doAfterTextChanged {
                //update data on change
                CampaignData.getCampaignList()[uuid!!].name = it.toString()
                writeDataToFile(CampaignData)
                supportActionBar?.title = it.toString()
            }
        }
        var descLbl = TextView(this).apply {
            setText(R.string.desc_lbl)
            setTextColor(Color.parseColor("#FFFFFF"))
        }
        var editDesc = EditText(this).apply {
            setText(CampaignData.getCampaignList()[uuid!!].desc)
            setTextColor(Color.parseColor("#FFFFFF"))
            doAfterTextChanged {
                //update data on change
                CampaignData.getCampaignList()[uuid!!].desc = it.toString()
                writeDataToFile(CampaignData)
            }
        }
        var controlLayout = LinearLayoutCompat(this).apply {
            id = View.generateViewId()
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            orientation = LinearLayoutCompat.VERTICAL
            addView(nameLbl)
            addView(editName)
            addView(descLbl)
            addView(editDesc)
        }
        val fragmentLinearLayout = LinearLayoutCompat(this).apply {
            id = View.generateViewId()
            fid = id
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
            (layoutParams as RelativeLayout.LayoutParams).addRule(
                RelativeLayout.BELOW, controlLayout.id)
        }
        val relativeLayout = RelativeLayout(this).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
            addView(controlLayout)
            addView(fragmentLinearLayout)
            setBackgroundColor(Color.parseColor("#282828"))
        }
        findViewById<ConstraintLayout>(R.id.campaign_detail_layout).addView(relativeLayout)
        createCampaignFragments()
    }
    //https://www.geeksforgeeks.org/actionbar-in-android-with-example/
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
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        // Delete
                        CampaignData.deleteCampaign(uuid!!)
                        writeDataToFile(CampaignData)
                        var player = MediaPlayer.create(this@CampaignDetailsActivity,R.raw.crumple)
                        player!!.start()
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
        return super.onOptionsItemSelected(item)
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
    private fun createCampaignFragments() {
            supportFragmentManager.commit {
                add(fid, CampaignObjectFragment.newInstance(uuid.toString(), "NPCs"), null)
                add(fid, CampaignObjectFragment.newInstance(uuid.toString(), "Cities"), null)
                add(fid, CampaignObjectFragment.newInstance(uuid.toString(), "Places"), null)
                add(fid, CampaignObjectFragment.newInstance(uuid.toString(), "Quests"), null)
                add(fid, CampaignObjectFragment.newInstance(uuid.toString(), "Items"), null)
            }
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
}