package com.example.rpgnotes

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import org.json.JSONArray
import java.io.*

class CampaignActivity : CampaignData.CampaignDataUpdateListener, AppCompatActivity() {
    private var fid = 0
    private lateinit var campaignData:CampaignData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign)
        val actionBar = supportActionBar
        actionBar!!.title = "Campaign Manager"
        val fragmentLinearLayout = LinearLayoutCompat(this).apply {
            id = View.generateViewId()
            fid = id
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
        }
        val relativeLayout = RelativeLayout(this).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
            addView(fragmentLinearLayout)
            setBackgroundColor(Color.parseColor("#282828"))
        }
        findViewById<ConstraintLayout>(R.id.campaign_layout).addView(relativeLayout)
        CampaignData.registerListener(this)
    }

    override fun updateCampaignDependents() {
        writeDataToFile(CampaignData)
    }

    private fun createCampaignFragments() {
        removeExistingCampaigns()
        for((index, item) in CampaignData.getCampaignList().withIndex()){
            supportFragmentManager.commit {
                add(fid, CampaignFragment.newInstance(index), null)
            }
        }
    }
    private fun removeExistingCampaigns(){
        for(frag in supportFragmentManager.fragments){
            supportFragmentManager.commit {
                remove(frag)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        val jsonResult = readDataAsJSON()
        if(jsonResult != null){
            loadJsonCampaign(jsonResult)
            createCampaignFragments()
        }
    }

    override fun onPause() {
        super.onPause()
        writeDataToFile(CampaignData)
    }
    //https://www.geeksforgeeks.org/actionbar-in-android-with-example/
    // the user opens the menu for the first time
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.campaign_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add-> {
                supportFragmentManager.commit {
                    if(supportFragmentManager.fragments.size < 9) {
                        CampaignData.addCampaign(Campaign("namevalue", "descvalue", null, null, null, null, null))
                        val uuid = CampaignData.getCampaignList().size - 1
                        add(fid, CampaignFragment.newInstance(uuid), null)
                        writeDataToFile(CampaignData)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
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
    private fun loadJsonCampaign(data: JSONArray) {
        CampaignData.loadCampaign(data)
    }
}