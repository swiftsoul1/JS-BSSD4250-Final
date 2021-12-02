package com.example.rpgnotes

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import org.json.JSONArray
import java.io.*

class CampaignObjectListActivity : AppCompatActivity() {
    companion object{
        const val uuid_key:String = "com.example.rpgnotes.uuid"
        const val type_key:String = "com.example.rpgnotes.type"
    }
    private var fid:Int = 0
    private var uuid:Int? = null
    private var type:String? = null
    var list:ArrayList<CampaignObject> = ArrayList<CampaignObject>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //layout
        setContentView(R.layout.activity_campaign_object_list)
        val fragmentLinearLayout = LinearLayoutCompat(this).apply {
            id = View.generateViewId()
            fid = id
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
        }
        val relativeLayout = RelativeLayout(this).apply {
            addView(fragmentLinearLayout)
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#282828"))
        }
        findViewById<ConstraintLayout>(R.id.campaign_object_list_layout).addView(relativeLayout)
        findViewById<ConstraintLayout>(R.id.campaign_object_list_layout).setBackgroundColor(Color.parseColor("#282828"))

        //data
        uuid = intent.getStringExtra(CampaignObjectListActivity.uuid_key)?.toInt()
        type = intent.getStringExtra(CampaignObjectListActivity.type_key)
        when(type){
            "NPCs" -> list = CampaignData.getCampaignList()[uuid!!].NPCs!!
            "Cities" -> list = CampaignData.getCampaignList()[uuid!!].Cities!!
            "Places" -> list = CampaignData.getCampaignList()[uuid!!].Places!!
            "Quests" -> list = CampaignData.getCampaignList()[uuid!!].Quests!!
            "Items" -> list = CampaignData.getCampaignList()[uuid!!].Items!!
        }
        val actionBar = supportActionBar
        actionBar!!.title = type.toString() + " Manager"
        createListFragments()
    }


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
                        when(type){
                            "NPCs" -> {
                                CampaignData.getCampaignList()[uuid!!].NPCs!!.add(CampaignObject("npc name", "npc desc", null, null))
                                writeDataToFile(CampaignData)
                                list = CampaignData.getCampaignList()[uuid!!].NPCs!!
                                updateListFragments()
                            }
                            "Cities" -> {
                                CampaignData.getCampaignList()[uuid!!].Cities!!.add(CampaignObject("city name", "city desc", null, null))
                                writeDataToFile(CampaignData)
                                list =CampaignData.getCampaignList()[uuid!!].Cities!!
                                updateListFragments()
                            }
                            "Places" -> {
                                CampaignData.getCampaignList()[uuid!!].Places!!.add(CampaignObject("place name", "place desc", null, null))
                                writeDataToFile(CampaignData)
                                list = CampaignData.getCampaignList()[uuid!!].Places!!
                                updateListFragments()
                            }
                            "Quests" -> {
                                CampaignData.getCampaignList()[uuid!!].Quests!!.add(CampaignObject("quest name", "quest desc", null, null))
                                writeDataToFile(CampaignData)
                                list = CampaignData.getCampaignList()[uuid!!].Quests!!
                                updateListFragments()
                            }
                            "Items" -> {
                                CampaignData.getCampaignList()[uuid!!].Items!!.add(CampaignObject("item name", "item desc", null, null))
                                writeDataToFile(CampaignData)
                                list = CampaignData.getCampaignList()[uuid!!].Items!!
                                updateListFragments()
                            }

                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateListFragments() {
        createListFragments()
    }
    private fun createListFragments() {
        removeExistingFragments()
        for((index, item) in list.withIndex()){
            supportFragmentManager.commit {
                add(fid, CampaignObjectItemFragment.newInstance(index, uuid.toString(), type.toString()), null)
            }
        }
    }

    private fun removeExistingFragments() {
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
            CampaignData.loadCampaign(jsonResult)
            updateListFragments()
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
}