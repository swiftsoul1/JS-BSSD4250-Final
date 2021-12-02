package com.example.rpgnotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GestureDetectorCompat
import org.json.JSONArray
import java.io.FileOutputStream
import java.lang.Exception
import java.io.*


private const val ARG_UUID = "com.example.rpgnotes.uuid"
class CampaignFragment : Fragment() {
    private val startForCampaignDetailsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }
    private lateinit var mDetector: GestureDetectorCompat
    private var uuid: Int? = null
    private lateinit var campaign:Campaign
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uuid = it.getInt(ARG_UUID)
        }
        campaign = CampaignData.getCampaignList()[uuid!!]
        mDetector = GestureDetectorCompat(context, MyGestureListener())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var nameView = TextView(requireContext()).apply {
            text = campaign.name
            setTextColor(Color.parseColor("#FFFFFF"))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        }
        var descView = TextView(requireContext()).apply {
            text = campaign.desc
            setTextColor(Color.parseColor("#FFFFFF"))
        }
        var ll = LinearLayoutCompat(requireContext()).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            setBackgroundResource(R.drawable.border_button)
            setPadding(30, 50, 30, 50)
            addView(nameView)
            addView(descView)
        }
        var llc = LinearLayoutCompat(requireContext()).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            setOnTouchListener(object:View.OnTouchListener{
                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    mDetector.onTouchEvent(p1)
                    return true
                }
            })
            setOnClickListener {

            }
            addView(ll)
        }
        return llc
    }
    private fun writeDataToFile(campaignData: CampaignData) {
        val file:String = "campaigns.json"
        val data:String = CampaignData.toJSON().toString()
        try {
            val fileOutputStream: FileOutputStream = requireContext().openFileOutput(file, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private inner class MyGestureListener : SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            val passableData = Intent(requireContext(), CampaignDetailsActivity::class.java).apply {
                putExtra(CampaignDetailsActivity.uuid_key, uuid.toString())
            }
            startForCampaignDetailsActivity.launch(passableData)
        }
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            try {
                val Y = Math.abs(e2.y - e1.y)
                val X = Math.abs(e2.x - e1.x)
                if (X > Y) {
                    if (X > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_THRESHOLD) {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Are you sure you want to Delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                // Delete
                                CampaignData.deleteCampaign(uuid!!)
                                writeDataToFile(CampaignData)
                                var player = MediaPlayer.create(requireContext(),R.raw.crumple)
                                player!!.start()
                                activity?.supportFragmentManager?.beginTransaction()?.remove(this@CampaignFragment)?.commit()
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(uuid:Int) =
            CampaignFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_UUID, uuid)
                }
            }
    }
}