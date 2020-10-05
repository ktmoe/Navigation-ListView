package com.dvinfosys.androidcustomnavigationdrawerusingexpandablelistview.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dvinfosys.androidcustomnavigationdrawerusingexpandablelistview.R
import com.dvinfosys.model.HeaderModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * Created by ktmmoe on 05, October, 2020
 **/
class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        expandable_navigation.init(this, "en", AppCompatDelegate.MODE_NIGHT_NO)
        expandable_navigation.addHeaderModel(HeaderModel())
        expandable_navigation.build()

    }

}