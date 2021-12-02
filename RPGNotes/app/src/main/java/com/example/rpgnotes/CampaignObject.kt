package com.example.rpgnotes
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList
class CampaignObject(var name:String, var desc:String, var notes:ArrayList<String>?, var images:ArrayList<String>?) {
    init{
        if(notes == null){
            notes = ArrayList<String>()
        }
        if(images == null){
            images = ArrayList<String>()
        }
    }
    public fun toJSON(): JSONObject {
        val jsonObject = JSONObject().apply{
            put("name", name)
            put("desc", desc)
            if(notes!!.isNotEmpty()){
                put("notes", JSONArray(notes?.toArray()))
            }
            if(images!!.isNotEmpty()){
                put("images", JSONArray(images?.toArray()))
            }
        }
        return jsonObject
    }
    override fun toString(): String {
        return "$name, $desc, $notes, $images"
    }
    fun addNote(note:String){
        notes?.add(note)
    }
    fun addImage(image:String){
        images?.add(image)
    }
}