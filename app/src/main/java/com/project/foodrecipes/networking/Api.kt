package com.project.foodrecipes.networking

/**
 * Created by Achmad Qomarudin on 19-10-2020.
 */

object Api {

    var Categories      = "https://www.themealdb.com/api/json/v1/1/categories.php"
    var Filter          = "https://www.themealdb.com/api/json/v1/1/filter.php?c={strCategory}"
    var DetailRecipe    = "https://www.themealdb.com/api/json/v1/1/lookup.php?i={idMeal}"

}