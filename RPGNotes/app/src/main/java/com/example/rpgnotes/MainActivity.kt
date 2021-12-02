package com.example.rpgnotes

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {


    private val startForCampaignActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var logoView = ImageView(this).apply {
            setImageResource(R.drawable.logo)
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
        }
        var campaignManagerBtn = Button(this).apply {
            setText(R.string.campainBtn)
            setBackgroundResource(R.drawable.border_button)
            setTextColor(Color.parseColor("#FFFFFF"))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                val passableData = Intent(applicationContext, CampaignActivity::class.java).apply {
                }
                startForCampaignActivity.launch(passableData)
            }
        }
        var rollerBtn = Button(this).apply {
            setText(R.string.rollerBtn)
            setBackgroundResource(R.drawable.border_button)
            setTextColor(Color.parseColor("#FFFFFF"))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                val passableData = Intent(applicationContext, DiceActivity::class.java).apply {
                }
                startForCampaignActivity.launch(passableData)
            }
        }
        var searchRefBtn = Button(this).apply{
            setText(R.string.searchBtn)
            setBackgroundResource(R.drawable.border_button)
            setTextColor(Color.parseColor("#FFFFFF"))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                val passableData = Intent(applicationContext, APIActivity::class.java).apply {
                }
                startForCampaignActivity.launch(passableData)
            }
        }
        val linearLayout = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#282828"))
            addView(logoView)
            addView(campaignManagerBtn)
            //addView(rollerBtn)
            //addView(searchRefBtn)
        }
        findViewById<ConstraintLayout>(R.id.main_layout).addView(linearLayout)
    }
}