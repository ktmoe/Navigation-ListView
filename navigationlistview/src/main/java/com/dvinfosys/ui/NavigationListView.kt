package com.dvinfosys.ui

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatDelegate
import com.dvinfosys.adapter.NavigationListAdapter
import com.dvinfosys.model.HeaderModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ktmmoe on 05, October, 2020
 **/
class NavigationListView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ExpandableListView(context, attrs, defStyleAttr){

    private var currentSelection = 0
    private var currentChildSelection = -1
    private var listHeader: MutableList<HeaderModel> = mutableListOf()
    private var adapter: NavigationListAdapter? = null
    private var groupLayoutTap: (Int)->Unit = {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasureSpecCustom: Int = MeasureSpec.makeMeasureSpec(
                Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, heightMeasureSpecCustom)
        val params: ViewGroup.LayoutParams = layoutParams
        params.height = measuredHeight
    }

    fun init(context: Context, language: String, mode: Int): NavigationListView {
        localeCorrect(context, language)
        AppCompatDelegate.setDefaultNightMode(mode)
        listHeader = ArrayList()
        return this
    }

    private fun localeCorrect(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun setListMenu(list: List<HeaderModel>?): NavigationListView {
        if (list != null) {
            listHeader.addAll(list)
        }
        return this
    }

    fun addOnExpandListener(onGroupExpandListener: OnGroupExpandListener) {
        setOnGroupExpandListener(onGroupExpandListener)
    }

    fun addOnCollapseListener(onGroupCollapseListener: OnGroupCollapseListener) {
        setOnGroupCollapseListener(onGroupCollapseListener)
    }

    fun addOnGroupClickListener(onGroupClickListener: OnGroupClickListener) {
        setOnGroupClickListener(onGroupClickListener)
    }

    fun addOnChildClickListener (onChildClickListener: OnChildClickListener) {
        setOnChildClickListener(onChildClickListener)
    }

    fun addHeaderModel(model: HeaderModel?){
        if (model != null) {
            listHeader.add(model)
        }
    }

    fun addGroupLayoutTap(tap:(Int)-> Unit) {
        groupLayoutTap = tap
    }

    fun build(): NavigationListView {
        adapter = NavigationListAdapter(context, listHeader, groupLayoutTap)
        setAdapter(adapter)
        return this
    }

    fun setSelected(groupPosition: Int) {
        val headerModel = listHeader[groupPosition]
        if (!headerModel.hasChild) {
            val currentModel = listHeader[currentSelection]
            currentModel.isSelected = false
            if (currentChildSelection != -1) {
                val childModel = listHeader[currentSelection]
                        .childModelList[currentChildSelection]
                childModel.isSelected = false
                currentChildSelection = -1
            }
            headerModel.isSelected = true
            currentSelection = groupPosition
            adapter!!.notifyDataSetChanged()
        }
    }

    fun setSelected(groupPosition: Int, childPosition: Int) {
        val currentModel = listHeader[currentSelection]
        currentModel.isSelected = false
        if (currentChildSelection != -1) {
            val currentChildModel = listHeader[currentSelection]
                    .childModelList[currentChildSelection]
            currentChildModel.isSelected = false
        }
        currentSelection = groupPosition
        currentChildSelection = childPosition
        val childModel = listHeader[groupPosition].childModelList[childPosition]
        childModel.isSelected = true
        adapter!!.notifyDataSetChanged()
    }
}