package com.csobrino.pruebatecnica.modules.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.csobrino.pruebatecnica.R
import com.csobrino.pruebatecnica.databinding.ActivityMainBinding
import com.csobrino.pruebatecnica.modules.main.crud.CrudFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.activity = this


        showCrudFragment()

    }

    private fun showCrudFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(
            binding.container.id,
            CrudFragment.newInstance(null),
            "crud"
        ).commit()
    }

}