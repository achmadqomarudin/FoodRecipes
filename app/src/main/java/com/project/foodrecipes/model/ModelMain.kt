package com.project.foodrecipes.model

import java.io.Serializable

/**
 * Created by Achmad Qomarudin on 19-10-2020.
 */

class ModelMain : Serializable {

    @JvmField
    var strCategory: String? = null

    @JvmField
    var strCategoryThumb: String? = null

    var strCategoryDescription: String? = null

}