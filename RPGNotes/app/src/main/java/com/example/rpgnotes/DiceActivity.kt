package com.example.rpgnotes

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout

class DiceActivity : AppCompatActivity() {
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private lateinit var iv: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)
        //https://www.tutorialkart.com/kotlin-android/draw-shape-to-canvas-example/
        bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        var d4 = RadioButton(this).apply{
            text = "d4"
            id = View.generateViewId()
        }
        var d6 = RadioButton(this).apply{
            text = "d6"
            id = View.generateViewId()
        }
        var d8 = RadioButton(this).apply{
            text = "d8"
            id = View.generateViewId()
        }
        var d12 = RadioButton(this).apply{
            text = "d12"
            id = View.generateViewId()
        }
        var d20 = RadioButton(this).apply{
            text = "d20"
            id = View.generateViewId()
        }
        var rGroup = RadioGroup(this).apply {
            addView(d4)
            addView(d6)
            addView(d8)
            addView(d12)
            addView(d20)
            id = View.generateViewId()
            setOnCheckedChangeListener { radioGroup, i ->
                val radio: RadioButton = findViewById(radioGroup.checkedRadioButtonId)
                when(radio.text.toString()){
                    "d4" -> {
                        drawSquare()
                    }
                }
            }
        }
        var relativeLayout = RelativeLayout(this).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            addView(rGroup)
        }
        iv = ImageView(this).apply {

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        var ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            addView(relativeLayout)
            addView(iv)
        }
        findViewById<ConstraintLayout>(R.id.dice_layout).setBackgroundColor(Color.parseColor("#282828"))
        findViewById<ConstraintLayout>(R.id.dice_layout).addView(ll)
    }

    private fun drawTriangle(bitmap:Bitmap, canvas: Canvas) {
        TODO("Not yet implemented")
    }
    private fun drawSquare() {
        // square positions
        var left = 200
        var top = 200
        var right = 400
        var bottom = 400

        // draw rectangle shape to canvas
        var shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#000000"))
        shapeDrawable.draw(canvas)

        iv.background = BitmapDrawable(getResources(), bitmap)

    }
    private fun drawDiamond(bitmap:Bitmap, canvas: Canvas) {
        TODO("Not yet implemented")
    }
    private fun drawDecagon(bitmap:Bitmap, canvas: Canvas) {
        TODO("Not yet implemented")
    }
    private fun drawHexagon(bitmap:Bitmap, canvas: Canvas) {
        TODO("Not yet implemented")
    }


}