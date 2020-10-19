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

                                    //Get Ingredient
                                    dataApi.strIngredient1 = temp.getString("strIngredient1")
                                    dataApi.strIngredient2 = temp.getString("strIngredient2")
                                    dataApi.strIngredient3 = temp.getString("strIngredient3")
                                    dataApi.strIngredient4 = temp.getString("strIngredient4")
                                    dataApi.strIngredient5 = temp.getString("strIngredient5")
                                    dataApi.strIngredient6 = temp.getString("strIngredient6")
                                    dataApi.strIngredient7 = temp.getString("strIngredient7")
                                    dataApi.strIngredient8 = temp.getString("strIngredient8")
                                    dataApi.strIngredient9 = temp.getString("strIngredient9")
                                    dataApi.strIngredient10 = temp.getString("strIngredient10")
                                    dataApi.strIngredient11 = temp.getString("strIngredient11")
                                    dataApi.strIngredient12 = temp.getString("strIngredient12")
                                    dataApi.strIngredient13 = temp.getString("strIngredient13")
                                    dataApi.strIngredient14 = temp.getString("strIngredient14")
                                    dataApi.strIngredient15 = temp.getString("strIngredient15")
                                    dataApi.strIngredient16 = temp.getString("strIngredient16")
                                    dataApi.strIngredient17 = temp.getString("strIngredient17")
                                    dataApi.strIngredient18 = temp.getString("strIngredient18")
                                    dataApi.strIngredient19 = temp.getString("strIngredient19")
                                    dataApi.strIngredient20 = temp.getString("strIngredient20")

                                    //Get Measure
                                    dataApi.strMeasure1 = temp.getString("strMeasure1")
                                    dataApi.strMeasure2 = temp.getString("strMeasure2")
                                    dataApi.strMeasure3 = temp.getString("strMeasure3")
                                    dataApi.strMeasure4 = temp.getString("strMeasure4")
                                    dataApi.strMeasure5 = temp.getString("strMeasure5")
                                    dataApi.strMeasure6 = temp.getString("strMeasure6")
                                    dataApi.strMeasure7 = temp.getString("strMeasure7")
                                    dataApi.strMeasure8 = temp.getString("strMeasure8")
                                    dataApi.strMeasure9 = temp.getString("strMeasure9")
                                    dataApi.strMeasure10 = temp.getString("strMeasure10")
                                    dataApi.strMeasure11 = temp.getString("strMeasure11")
                                    dataApi.strMeasure12 = temp.getString("strMeasure12")
                                    dataApi.strMeasure13 = temp.getString("strMeasure13")
                                    dataApi.strMeasure14 = temp.getString("strMeasure14")
                                    dataApi.strMeasure15 = temp.getString("strMeasure15")
                                    dataApi.strMeasure16 = temp.getString("strMeasure16")
                                    dataApi.strMeasure17 = temp.getString("strMeasure17")
                                    dataApi.strMeasure18 = temp.getString("strMeasure18")
                                    dataApi.strMeasure19 = temp.getString("strMeasure19")
                                    dataApi.strMeasure20 = temp.getString("strMeasure20")

                                    //set Ingredients
                                    if (dataApi.strIngredient1.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient1)
                                    }
                                    if (dataApi.strIngredient2.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient2)
                                    }
                                    if (dataApi.strIngredient3.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient3)
                                    }
                                    if (dataApi.strIngredient4.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient4)
                                    }
                                    if (dataApi.strIngredient5.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient5)
                                    }
                                    if (dataApi.strIngredient6.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient6)
                                    }
                                    if (dataApi.strIngredient7.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient7)
                                    }
                                    if (dataApi.strIngredient8.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient8)
                                    }
                                    if (dataApi.strIngredient9.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient9)
                                    }
                                    if (dataApi.strIngredient10.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient10)
                                    }
                                    if (dataApi.strIngredient11.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient11)
                                    }
                                    if (dataApi.strIngredient12.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient12)
                                    }
                                    if (dataApi.strIngredient13.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient13)
                                    }
                                    if (dataApi.strIngredient14.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient14)
                                    }
                                    if (dataApi.strIngredient15.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient15)
                                    }
                                    if (dataApi.strIngredient16.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient16)
                                    }
                                    if (dataApi.strIngredient17.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient17)
                                    }
                                    if (dataApi.strIngredient18.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient18)
                                    }
                                    if (dataApi.strIngredient19.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient19)
                                    }
                                    if (dataApi.strIngredient20.isNotEmpty()) {
                                        tvIngredients.append("\n \u2022 " + dataApi.strIngredient20)
                                    }

                                    //set StrMeasure
                                    if (dataApi.strMeasure1.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure1)
                                    }
                                    if (dataApi.strMeasure2.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure2)
                                    }
                                    if (dataApi.strMeasure3.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure3)
                                    }
                                    if (dataApi.strMeasure4.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure4)
                                    }
                                    if (dataApi.strMeasure5.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure5)
                                    }
                                    if (dataApi.strMeasure6.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure6)
                                    }
                                    if (dataApi.strMeasure7.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure7)
                                    }
                                    if (dataApi.strMeasure8.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure8)
                                    }
                                    if (dataApi.strMeasure9.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure9)
                                    }
                                    if (dataApi.strMeasure10.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure10)
                                    }
                                    if (dataApi.strMeasure11.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure11)
                                    }
                                    if (dataApi.strMeasure12.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure12)
                                    }
                                    if (dataApi.strMeasure13.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure13)
                                    }
                                    if (dataApi.strMeasure14.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure14)
                                    }
                                    if (dataApi.strMeasure15.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure15)
                                    }
                                    if (dataApi.strMeasure16.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure16)
                                    }
                                    if (dataApi.strMeasure17.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure17)
                                    }
                                    if (dataApi.strMeasure18.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure18)
                                    }
                                    if (dataApi.strMeasure19.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure19)
                                    }
                                    if (dataApi.strMeasure20.isNotEmpty()) {
                                        tvMeasure.append("\n : " + dataApi.strMeasure20)
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