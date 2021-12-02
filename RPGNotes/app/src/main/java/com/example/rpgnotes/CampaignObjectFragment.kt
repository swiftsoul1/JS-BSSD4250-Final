package com.example.rpgnotes

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat

private const val ARG_UUID = "com.example.rpgnotes.uuid"
private const val ARG_TYPE = "com.example.rpgnotes.type"
class CampaignObjectFragment : Fragment() {
    private val startForListActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }
    private var uuid: String? = null
    private var type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uuid = it.getString(ARG_UUID)
            type = it.getString(ARG_TYPE)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var typeView = TextView(requireContext()).apply {
            text = type
            setTextColor(Color.parseColor("#FFFFFF"))
        }
        var container = LinearLayoutCompat(requireContext()).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.border_button)
            addView(typeView)
            setOnClickListener {
                //some mismatch results in null=type without this temp var
                var temp = type.toString()
                val passableData = Intent(requireContext(), CampaignObjectListActivity::class.java).apply {
                    putExtra(CampaignObjectListActivity.uuid_key, uuid.toString())
                    putExtra(CampaignObjectListActivity.type_key, temp)
                }
                startForListActivity.launch(passableData)
            }
        }
        return container
    }
    companion object {
        @JvmStatic
        fun newInstance(uuid: String, type:String) =
            CampaignObjectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UUID, uuid)
                    putString(ARG_TYPE, type)
                }
            }
    }
}