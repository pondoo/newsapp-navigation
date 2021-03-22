package com.pondoo.application.ui.navigation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pondoo.application.R
import com.pondoo.application.databinding.FragmentHomeBinding
import com.pondoo.application.model.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment()  {
    private val mViewModel : HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainAdapter: ViewPagerAdapter
    var ct: Context?=null
    var list:List<Article>?=null
    val categories: Array<String> = arrayOf("business","entertainment","general","health"
            ,"science","sports","technology")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        val view=binding.root
        ct=activity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inits()
        initPager()
    }
    private fun inits(){
        initPager()
        observeLiveData()
        var adapter= ArrayAdapter(ct!!, R.layout.spinner_text,categories)
        binding.HomeFragmentCategorySpinner.adapter=adapter
        mViewModel.apiCall(ct!!,categories.get(0))
        setOnClickListeners()

    }

    fun initPager(){
        mainAdapter= ViewPagerAdapter(ct!!)
        binding.HomeFragmentViewPager.adapter=
            mainAdapter
    }
    fun observeLiveData(){
        mViewModel.articleList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            list=it
            mainAdapter.updateList(it)
        })
    }
    fun setOnClickListeners(){
        binding.HomeFragmentToLeft.setOnClickListener(View.OnClickListener {
            var i = binding.HomeFragmentViewPager.currentItem
            if(i!=0) {
                i--
                binding.HomeFragmentViewPager.setCurrentItem(i, true)
            }
        })
        binding.HomeFragmentToRight.setOnClickListener(View.OnClickListener {
            var i = binding.HomeFragmentViewPager.currentItem
            if(i!=list!!.size) {
                i++
                binding.HomeFragmentViewPager.setCurrentItem(i, true)
            }else{
                binding.HomeFragmentViewPager.setCurrentItem(0, true)
            }
        })
        binding.HomeFragmentCategorySpinner.setOnItemSelectedListener(object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mViewModel.apiCall(ct!!,categories.get(position))
            }

        }
        )


    }
}