package com.project.foodrecipes.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.project.foodrecipes.R
import com.project.foodrecipes.model.ModelDetailRecipe
import com.project.foodrecipes.model.ModelFilter
import com.project.foodrecipes.networking.Api
import kotlinx.android.synthetic.main.activity_detail_recipe.*
import org.json.JSONException
import org.json.JSONObject

@Suppress("DEPRECATION")
class DetailRecipeActivity : AppCompatActivity() {

    var idMeal: String? = null
    var strMeal: String? = null
    var modelFilter: ModelFilter? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recipe)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        toolbar_detail.title = null
        setSupportActionBar(toolbar_detail)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.setMessage("Displaying data ...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)

        //Button Favorite
        imgFavorite!!.setOnClickListener {
            Toast.makeText(this@DetailRecipeActivity, "Feature under development", Toast.LENGTH_SHORT).show()
        }

        //Get intent FilterFoodActivity
        modelFilter = intent.getSerializableExtra("detailRecipe") as ModelFilter
        if (modelFilter != null) {
            idMeal = modelFilter!!.idMeal
            strMeal = modelFilter!!.strMeal

            //Set text
            tvTitle.text = strMeal
            tvTitle.isSelected = true

            //Get image source
            Glide.with(this)
                    .load(modelFilter!!.strMealThumb)
                    .placeholder(R.drawable.ic_food_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgThumb)

            //Method get data
            detailRecipe
        }
    }

    private val detailRecipe: Unit
        private get() {
            progressDialog!!.show()
            AndroidNetworking.get(Api.DetailRecipe)
                    .addPathParameter("idMeal", idMeal)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            try {
                                progressDialog!!.dismiss()
                                val playerArray = response.getJSONArray("meals")
                                for (i in 0 until playerArray.length()) {

                                    val temp = playerArray.getJSONObject(i)
                                    val dataApi = ModelDetailRecipe()
                                    val Instructions = temp.getString("strInstructions")
                                    tvInstructions!!.text = Instructions

                                    val Category = temp.getString("strCategory")
                                    val Area = temp.getString("strArea")
                                    tvSubTitle!!.text = "$Category | $Area"

                                    val Source = temp.getString("strSource")
                                    tvSource!!.setOnClickListener { v: View? ->
                                        val intentYoutube = Intent(Intent.ACTION_VIEW)
                                        intentYoutube.data = Uri.parse(Source)
                                        startActivity(intentYoutube)
                                    }

                                    val Youtube = temp.getString("strYoutube")
                                    tvYoutube!!.setOnClickListener { v: View? ->
                                        val intentYoutube = Intent(Intent.ACTION_VIEW)
                                        intentYoutube.data = Uri.parse(Youtube)
                                        startActivity(intentYoutube)
                                    }

                                    val ShareRecipe = temp.getString("strSource")
                                    tvShareRecipe!!.setOnClickListener {
                                        val shareIntent = Intent()
                                        shareIntent.action = Intent.ACTION_SEND
                                        shareIntent.type="text/plain"
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, ShareRecipe);
                                        startActivity(Intent.createChooser(shareIntent, "Share with"))
                                    }

                                    for (n in 1 .. 20){
                                        val ingredient = temp.getString("strIngredient$n")
                                        val measure = temp.getString("strMeasure$n")
                                        if (ingredient.trim() != "" && ingredient.trim() != "null") tvIngredients.append("\n \u2022 $ingredient")
                                        if (measure.trim() != "" && measure.trim() != "null") tvMeasure.append("\n : $measure")
                                    }

                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(this@DetailRecipeActivity,
                                        "Gagal menampilkan data!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(anError: ANError) {
                            progressDialog!!.dismiss()
                            Toast.makeText(this@DetailRecipeActivity,"No internet connection!", Toast.LENGTH_SHORT).show()
                        }
                    })
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        //Set Transparent Status bar
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }
}