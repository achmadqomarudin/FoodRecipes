package com.project.foodrecipes.model

import java.io.Serializable

/**
 * Created by Achmad Qomarudin on 19-10-2020.
 */

class ModelFilter : Serializable {

    var idMeal: String? = null

    @JvmField
    var strMeal: String? = null

    @JvmField
    var strMealThumb: String? = null

}