package com.example.rpgnotes

import org.json.JSONObject

class Campaign(var name:String, var desc:String, var NPCs:ArrayList<CampaignObject>?,
                    var Cities:ArrayList<CampaignObject>?, var Places:ArrayList<CampaignObject>?, var Quests:ArrayList<CampaignObject>?,
                        var Items:ArrayList<CampaignObject>?){
    init{
        if(NPCs == null){
            NPCs = ArrayList<CampaignObject>()
        }
        if(Cities == null){
            Cities = ArrayList<CampaignObject>()
        }
        if(Places == null){
            Places = ArrayList<CampaignObject>()
        }
        if(Quests == null){
            Quests = ArrayList<CampaignObject>()
        }
        if(Items == null){
            Items = ArrayList<CampaignObject>()
        }
    }

    fun toJSON(): JSONObject {
        val jsonObject = JSONObject().apply{
            put("cname", name)
            put("cdesc", desc)
            if(NPCs!!.isNotEmpty()){
                NPCs!!.forEachIndexed{ index, npc ->
                    put("npc$index", npc.toJSON())
                }
            }
            if(Cities!!.isNotEmpty()){
                Cities!!.forEachIndexed{ index, city ->
                    put("city$index", city.toJSON())
                }
            }
            if(Places!!.isNotEmpty()){
                Places!!.forEachIndexed{ index, place ->
                    put("place$index", place.toJSON())
                }
            }
            if(Quests!!.isNotEmpty()){
                Quests!!.forEachIndexed{ index, quest ->
                    put("quest$index", quest.toJSON())
                }
            }
            if(Items!!.isNotEmpty()){
                Items!!.forEachIndexed{ index, item ->
                    put("item$index", item.toJSON())
                }
            }
        }
        return jsonObject
    }
    /*override fun toString(): String {
        return "$name, $desc, $notes, $images"
    }*/
}