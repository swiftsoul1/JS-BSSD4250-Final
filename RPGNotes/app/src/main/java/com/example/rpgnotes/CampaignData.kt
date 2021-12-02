package com.example.rpgnotes

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

object CampaignData {
    private val campaignData:ArrayList<Campaign> = ArrayList<Campaign>()
    interface CampaignDataUpdateListener{
        public fun updateCampaignDependents()
    }
    private var mListener:CampaignDataUpdateListener? = null
    fun registerListener(listener:CampaignDataUpdateListener){
        mListener = listener
    }
    fun deleteCampaign(index:Int){
        campaignData.removeAt(index)
        //breaks on this line
       mListener?.updateCampaignDependents()
    }
    fun addCampaign(campaign:Campaign){
        campaignData.add(campaign)
    }
    fun getCampaignList():ArrayList<Campaign>{
        return campaignData
    }
    fun toJSON(): JSONArray {
        val jsonArray = JSONArray()
        for(campaign in campaignData){
            jsonArray.put(campaign.toJSON())
        }
        return jsonArray
    }

    fun loadCampaign(data: JSONArray){
        for((index, item) in getCampaignList().withIndex()){
            deleteCampaign(index)
        }
        //for each saved data entry
        for(i in 0 until data.length()){
            val obj = data.getJSONObject(i)

            //region read_data
            //create NPC list
            var i = 0;
            var npcs:ArrayList<CampaignObject>? = null
            if(obj.has("npc$i")) {
                npcs = ArrayList<CampaignObject>()
                while (obj.has("npc$i")) {
                    var jsonResult = JSONObject(obj.getString("npc$i"))
                    var notes: ArrayList<String>? = null
                    if (jsonResult.has("notes")) {
                        var jsonNotes = jsonResult.getJSONArray("notes")
                        var notes: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonNotes.isNull(j)) {
                            notes.add(jsonNotes.getString(j))
                            j++
                        }
                    }
                    var images: ArrayList<String>? = null
                    if (jsonResult.has("images")) {
                        var jsonImages = jsonResult.getJSONArray("images")
                        var images: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonImages.isNull(j)) {
                            images.add(jsonImages.getString(j))
                            j++
                        }
                    }
                    npcs.add(
                        CampaignObject(
                            jsonResult.getString("name"),
                            jsonResult.getString("desc"),
                            notes,
                            images
                        )
                    )
                    i++
                }
            }
            //create city list
            i = 0;
            var cities:ArrayList<CampaignObject>? = null
            if(obj.has("city$i")) {
                cities = ArrayList<CampaignObject>()
                while (obj.has("city$i")) {
                    var jsonResult = JSONObject(obj.getString("city$i"))
                    var notes: ArrayList<String>? = null
                    if (jsonResult.has("notes")) {
                        var jsonNotes = jsonResult.getJSONArray("notes")
                        var notes: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonNotes.isNull(j)) {
                            notes.add(jsonNotes.getString(j))
                            j++
                        }
                    }
                    var images: ArrayList<String>? = null
                    if (jsonResult.has("images")) {
                        var jsonImages = jsonResult.getJSONArray("images")
                        var images: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonImages.isNull(j)) {
                            images.add(jsonImages.getString(j))
                            j++
                        }
                    }
                    cities.add(
                        CampaignObject(
                            jsonResult.getString("name"),
                            jsonResult.getString("desc"),
                            notes,
                            images
                        )
                    )
                    i++
                }
            }
            //create places list
            i = 0;
            var places:ArrayList<CampaignObject>? = null
            if(obj.has("city$i")) {
                places = ArrayList<CampaignObject>()
                while (obj.has("place$i")) {
                    var jsonResult = JSONObject(obj.getString("city$i"))
                    var notes: ArrayList<String>? = null
                    if (jsonResult.has("place")) {
                        var jsonNotes = jsonResult.getJSONArray("notes")
                        var notes: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonNotes.isNull(j)) {
                            notes.add(jsonNotes.getString(j))
                            j++
                        }
                    }
                    var images: ArrayList<String>? = null
                    if (jsonResult.has("images")) {
                        var jsonImages = jsonResult.getJSONArray("images")
                        var images: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonImages.isNull(j)) {
                            images.add(jsonImages.getString(j))
                            j++
                        }
                    }
                    places.add(
                        CampaignObject(
                            jsonResult.getString("name"),
                            jsonResult.getString("desc"),
                            notes,
                            images
                        )
                    )
                    i++
                }
            }
            //create quest list
            i = 0;
            var quests:ArrayList<CampaignObject>? = null
            if(obj.has("quest$i")) {
                quests = ArrayList<CampaignObject>()
                while (obj.has("quest$i")) {
                    var jsonResult = JSONObject(obj.getString("city$i"))
                    var notes: ArrayList<String>? = null
                    if (jsonResult.has("notes")) {
                        var jsonNotes = jsonResult.getJSONArray("notes")
                        var notes: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonNotes.isNull(j)) {
                            notes.add(jsonNotes.getString(j))
                            j++
                        }
                    }
                    var images: ArrayList<String>? = null
                    if (jsonResult.has("images")) {
                        var jsonImages = jsonResult.getJSONArray("images")
                        var images: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonImages.isNull(j)) {
                            images.add(jsonImages.getString(j))
                            j++
                        }
                    }
                    quests.add(
                        CampaignObject(
                            jsonResult.getString("name"),
                            jsonResult.getString("desc"),
                            notes,
                            images
                        )
                    )
                    i++
                }
            }
            //create items list
            i = 0;
            var items:ArrayList<CampaignObject>? = null
            if(obj.has("item$i")) {
                items = ArrayList<CampaignObject>()
                while (obj.has("item$i")) {
                    var jsonResult = JSONObject(obj.getString("item$i"))
                    var notes: ArrayList<String>? = null
                    if (jsonResult.has("notes")) {
                        var jsonNotes = jsonResult.getJSONArray("notes")
                        var notes: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonNotes.isNull(j)) {
                            notes.add(jsonNotes.getString(j))
                            j++
                        }
                    }
                    var images: ArrayList<String>? = null
                    if (jsonResult.has("images")) {
                        var jsonImages = jsonResult.getJSONArray("images")
                        var images: ArrayList<String> = ArrayList<String>()
                        var j = 0
                        while (!jsonImages.isNull(j)) {
                            images.add(jsonImages.getString(j))
                            j++
                        }
                    }
                    items.add(
                        CampaignObject(
                            jsonResult.getString("name"),
                            jsonResult.getString("desc"),
                            notes,
                            images
                        )
                    )
                    i++
                }
            }
            //endregion

            //put all the campaign pieces together
            addCampaign(Campaign(obj.getString("cname"), obj.getString("cdesc"), npcs, cities, places, quests, items))
        }
    }
}