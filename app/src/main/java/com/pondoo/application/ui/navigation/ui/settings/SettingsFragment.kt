package com.pondoo.application.ui.navigation.ui.settings

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pondoo.application.R
import com.pondoo.application.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    var ct:Context?=null
    private lateinit var binding: FragmentSettingsBinding
        val countryCodes: Array<String> = arrayOf("ar","at","au","be",
        "bg","br","ca","cn","co","cu","cz","de",
        "eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv"
        ,"ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","sa","si","sk","th"
        ,"tr","tw","ua","us","ve","za")
        val countryNames: Array<String> = arrayOf("Argentina","Austria","Australia","Belgium",
        "Bulgaria","Brazil","Canada","China","Colombia","Cuba","Czech Republic","Germany",
        "Egypt","France","United Kingdom","Greece","Hong Kong","Hungary","Indonesia","Ireland","Israel","India","Italy","Japan","Korea","Lithuania","Latvia"
        ,"Morocco","Mexico","Malaysia","Nigeria","Netherland","Norway","New Zealand","Philippines","Poland","Portugal","Romania",
            "serbia","Singapore","Slovenia","Slovakia","Thailand"
        ,"Turkey","Taiwan","Ukraine","United States","Venezuela","South Africa")
    val odd: IntArray = intArrayOf(1, 3, 5)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater,container,false)
        val view=binding.root
        ct=activity

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inits()

    }
    private fun inits(){
        var adapter= ArrayAdapter(ct!!, R.layout.spinner_text,countryNames)
        binding.SettingsFragmentCountryList.adapter=adapter
        val prefences = ct!!.getSharedPreferences("Utils", Context.MODE_PRIVATE)
        var pos=prefences.getInt("position",0)
        binding.SettingsFragmentCountryList.setSelection(pos)
        setOnClicks()

        mOnNavigationItemSelectedListener

    }
    private fun setOnClicks(){
        val prefences = ct!!.getSharedPreferences("Utils", Context.MODE_PRIVATE)
        val editor = prefences.edit()
        var isChecked= prefences.getBoolean("night",false)
        if(isChecked){
            binding.SettingsFragmentSwitchMode.isChecked=true
        }else{
            binding.SettingsFragmentSwitchMode.isChecked=false
        }
        binding.SettingsFragmentSwitchMode.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            try{
            if(isChecked!!){
                editor.putBoolean("night",true)

            }else{
                editor.putBoolean("night",false)
            }
            }catch (e:Exception){
                e.printStackTrace()
            }
            editor.apply()
            activity!!.recreate()
        })
        binding.SettingsFragmentCountryList.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
            ) {
                editor.putString("country",countryCodes.get(position))
                editor.putInt("position",position)
                editor.apply()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })
    }
    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_home->{
                        (ct as Activity).onBackPressed()
                    }
                }
                return false
            }
        }

}