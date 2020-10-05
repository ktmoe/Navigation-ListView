package com.dvinfosys.model

/**
 * Created by ktmmoe on 05, October, 2020
 **/
data class HeaderModel (
        val categoryId: String ="",
        var menuName: String = "",
        var menuIcon: Int = 0,
        var indicatorResource : Int= -1,
        var hasChild: Boolean = false,
        var isSelected: Boolean = false,
        var childModelList: List<ChildModel> = emptyList()
)