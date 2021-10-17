package com.csobrino.pruebatecnica.modules.main.crud

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.csobrino.pruebatecnica.R
import com.csobrino.pruebatecnica.data.Planet
import com.csobrino.pruebatecnica.databinding.ActivityDetailCrudBinding
import com.csobrino.pruebatecnica.databinding.DialogImageBinding


class CrudDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCrudBinding
    private lateinit var viewModel: CrudDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_crud)
        viewModel = ViewModelProvider(this).get(CrudDetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.acitivity = this
        binding.viewModel = viewModel

        initObservers()
        configToolbar()
        viewModel.planet.value = intent.getSerializableExtra("planet") as? Planet
    }

    private fun configToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initObservers() {
        viewModel.isEditMode.observe(this) {
            binding.editDate.isEnabled = it
            binding.editCopyright.isEnabled = it
            binding.editTitle.isEnabled = it
            binding.editDescription.isEnabled = it
        }

        viewModel.planet.observe(this) {
            Glide.with(this).load(it.hdurl).into(binding.image)
        }
    }

    fun showModalImage() {
        val dialogBinding: DialogImageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.dialog_image,
                null,
                false
            )

        val dialog = Dialog(this)
        dialogBinding.lifecycleOwner = this

        viewModel.planet.value?.hdurl?.let {
            Glide.with(this).load(it).into(dialogBinding.imageLayout)
        }

        dialogBinding.imageClose.setOnClickListener { dialog.dismiss() }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(Gravity.CENTER)

        dialog.show()
    }
}