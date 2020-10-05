package com.dvinfosys.adapter

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.dvinfosys.R
import com.dvinfosys.model.ChildModel
import com.dvinfosys.model.HeaderModel

/**
 * Created by ktmmoe on 05, October, 2020
 **/
class NavigationListAdapter(
        private val context: Context,
        private val listHeader: List<HeaderModel>,
        private val groupLayoutTap: (Int)->Unit
) :
        BaseExpandableListAdapter() {

    private val mmTypeFace = Typeface.createFromAsset(context.assets, "fonts/myanmarpixel.ttf")
    private val enTypeFace = Typeface.createFromAsset(context.assets, "fonts/dinpro.otf")

    override fun getChild(groupPosition: Int, childPosititon: Int): Any {
        return listHeader[groupPosition].childModelList[childPosititon]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getChildView(
            groupPosition: Int, childPosition: Int,
            isLastChild: Boolean, convertView: View?, parent: ViewGroup?
    ): View? {
        var view: View? = convertView
        val childText: ChildModel = getChild(groupPosition, childPosition) as ChildModel
        if (view == null) {
            val inflater: LayoutInflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item, null)
        }
        val txtListChild: TextView = view!!.findViewById(R.id.lblListItem)
        txtListChild.typeface = if (context.resources.configuration.locales[0].toLanguageTag() == "en") enTypeFace else mmTypeFace
        txtListChild.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))

        txtListChild.text = childText.menuName

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listHeader[groupPosition].childModelList.count()
    }

    override fun getGroup(groupPosition: Int): Any {
        return listHeader[groupPosition]
    }

    override fun getGroupCount(): Int {
        return listHeader.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getGroupView(
            groupPosition: Int, isExpanded: Boolean,
            convertView: View?, parent: ViewGroup?
    ): View? {
        var view: View? = convertView
        val header: HeaderModel = getGroup(groupPosition) as HeaderModel
        if (view == null) {
            val inflater: LayoutInflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_group, null)
        }
        val lblListHeader: TextView? = view?.findViewById(R.id.lblListHeader)
        val ivGroupIndicator: ImageView? = view?.findViewById(R.id.ivGroupIndicator)
        val iconMenu: ImageView? = view?.findViewById(R.id.icon_menu)
        lblListHeader?.typeface = if (context.resources.configuration.locales[0].toLanguageTag() == "en") enTypeFace else mmTypeFace
        lblListHeader?.text = header.menuName
        lblListHeader?.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))

        iconMenu?.setBackgroundResource(header.menuIcon)
        iconMenu?.visibility = View.VISIBLE

        if (header.hasChild) {
            ivGroupIndicator?.visibility = View.VISIBLE
        } else {
            ivGroupIndicator?.visibility = View.GONE
        }

        lblListHeader?.setOnClickListener { groupLayoutTap.invoke(groupPosition) }

        if (isExpanded) {
            ivGroupIndicator?.setImageResource(R.drawable.ic_remove)
        } else {
            ivGroupIndicator?.setImageResource(R.drawable.ic_add)
        }
        return view
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}

fun Context.fonts(view: TextView, resource: Int) {
    if (Build.VERSION.SDK_INT < 23) {
        view.setTextAppearance(this, resource)
    } else {
        view.setTextAppearance(resource)
    }
}