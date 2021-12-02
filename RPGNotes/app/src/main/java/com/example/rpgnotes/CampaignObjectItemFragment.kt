package com.example.rpgnotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.GestureDetectorCompat
import java.io.FileOutputStream
import java.lang.Exception

private const val ARG_INDEX = "com.example.rpgnotes.index"
private const val ARG_UUID = "com.example.rpgnotes.uuid"
private const val ARG_TYPE = "com.example.rpgnotes.type"
class CampaignObjectItemFragment : Fragment() {
    private var index: Int? = null
    private var uuid: Int? = null
    private var typeObj: String? = null
    private lateinit var mDetector: GestureDetectorCompat
    private val startForListDetails =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }

    var list:ArrayList<CampaignObject> = ArrayList<CampaignObject>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            index = it.getInt(ARG_INDEX)
            uuid = it.getString(ARG_UUID)?.toInt()
            typeObj = it.getString(ARG_TYPE)
        }
        when(typeObj){
            "NPCs" -> list = CampaignData.getCampaignList()[uuid!!].NPCs!!
            "Cities" -> list = CampaignData.getCampaignList()[uuid!!].Cities!!
            "Places" -> list = CampaignData.getCampaignList()[uuid!!].Places!!
            "Quests" -> list = CampaignData.getCampaignList()[uuid!!].Quests!!
            "Items" -> list = CampaignData.getCampaignList()[uuid!!].Items!!
        }
        mDetector = GestureDetectorCompat(context, MyGestureListener())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var nameView = TextView(requireContext()).apply {
            text = list[index!!].name
            setTextColor(Color.parseColor("#FFFFFF"))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
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
        }
        var llc = LinearLayoutCompat(requireContext()).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            setOnTouchListener(object:View.OnTouchListener{
                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    mDetector.onTouchEvent(p1)
                    return true
                }
            })
            setBackgroundColor(Color.parseColor("#282828"))
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
    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            val passableData = Intent(requireContext(), CampaignObjectItemDetailsActivity::class.java).apply {
                putExtra(CampaignObjectItemDetailsActivity.uuid_key, uuid.toString())
                putExtra(CampaignObjectItemDetailsActivity.index_key, index.toString())
                putExtra(CampaignObjectItemDetailsActivity.type_key, typeObj.toString())
            }
            startForListDetails.launch(passableData)
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
                        when(typeObj) {
                            "NPCs" -> {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setMessage("Are you sure you want to Delete?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->
                                        // Delete
                                        CampaignData.getCampaignList()[uuid!!].NPCs!!.removeAt(index!!)
                                        var player = MediaPlayer.create(requireContext(),R.raw.crumple)
                                        player!!.start()
                                        writeDataToFile(CampaignData)
                                        activity?.supportFragmentManager?.beginTransaction()?.remove(this@CampaignObjectItemFragment)?.commit()
                                    }
                                    .setNegativeButton("No") { dialog, id ->
                                        // Dismiss the dialog
                                        dialog.dismiss()
                                    }
                                val alert = builder.create()
                                alert.show()

                            }
                            "Cities" -> {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setMessage("Are you sure you want to Delete?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->
                                        // Delete
                                        CampaignData.getCampaignList()[uuid!!].Cities!!.removeAt(index!!)
                                        var player = MediaPlayer.create(requireContext(),R.raw.crumple)
                                        player!!.start()
                                        writeDataToFile(CampaignData)
                                        activity?.supportFragmentManager?.beginTransaction()?.remove(this@CampaignObjectItemFragment)?.commit()
                                    }
                                    .setNegativeButton("No") { dialog, id ->
                                        // Dismiss the dialog
                                        dialog.dismiss()
                                    }
                                val alert = builder.create()
                                alert.show()

                            }
                            "Places" -> {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setMessage("Are you sure you want to Delete?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->
                                        // Delete
                                        CampaignData.getCampaignList()[uuid!!].Places!!.removeAt(index!!)
                                        var player = MediaPlayer.create(requireContext(),R.raw.crumple)
                                        player!!.start()
                                        writeDataToFile(CampaignData)
                                        activity?.supportFragmentManager?.beginTransaction()?.remove(this@CampaignObjectItemFragment)?.commit()
                                    }
                                    .setNegativeButton("No") { dialog, id ->
                                        // Dismiss the dialog
                                        dialog.dismiss()
                                    }
                                val alert = builder.create()
                                alert.show()

                            }
                            "Quests" -> {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setMessage("Are you sure you want to Delete?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->
                                        // Delete
                                        CampaignData.getCampaignList()[uuid!!].Quests!!.removeAt(index!!)
                                        var player = MediaPlayer.create(requireContext(),R.raw.crumple)
                                        player!!.start()
                                        writeDataToFile(CampaignData)
                                        activity?.supportFragmentManager?.beginTransaction()?.remove(this@CampaignObjectItemFragment)?.commit()
                                    }
                                    .setNegativeButton("No") { dialog, id ->
                                        // Dismiss the dialog
                                        dialog.dismiss()
                                    }
                                val alert = builder.create()
                                alert.show()

                            }
                            "Items" -> {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setMessage("Are you sure you want to Delete?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->
                                        // Delete
                                        CampaignData.getCampaignList()[uuid!!].Items!!.removeAt(index!!)
                                        var player = MediaPlayer.create(requireContext(),R.raw.crumple)
                                        player!!.start()
                                        writeDataToFile(CampaignData)
                                        activity?.supportFragmentManager?.beginTransaction()?.remove(this@CampaignObjectItemFragment)?.commit()
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
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return true
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(index:Int, uuid: String, type:String) =
            CampaignObjectItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                    putString(ARG_UUID, uuid)
                    putString(ARG_TYPE, type)
                }
            }
    }

}