package com.dvinfosys.model

/**
 * Created by ktmmoe on 05, August, 2020
 **/

data class ChildModel (
        val categoryId: String = "1",
        val menuName: String = "World",
        var isSelected: Boolean = false
)