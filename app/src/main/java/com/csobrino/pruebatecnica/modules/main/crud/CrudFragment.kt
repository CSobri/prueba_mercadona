package com.csobrino.pruebatecnica.modules.main.crud

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.csobrino.pruebatecnica.R
import com.csobrino.pruebatecnica.adapters.CrudAdapter
import com.csobrino.pruebatecnica.data.Product
import com.csobrino.pruebatecnica.databinding.DialogImageBinding
import com.csobrino.pruebatecnica.databinding.FragmentCrudBinding
import com.csobrino.pruebatecnica.helpers.Constants
import com.google.android.material.snackbar.Snackbar


class CrudFragment : Fragment() {
    private lateinit var crudAdapter: CrudAdapter
    private lateinit var binding: FragmentCrudBinding
    private lateinit var viewModel: CrudViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crud, container, false)
        viewModel = ViewModelProvider(this).get(CrudViewModel::class.java)

        binding.lifecycleOwner = this
        binding.fragment = this

        initAdapter()
        initObservers()

        viewModel.getPlanetList()
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): Fragment {
            val fragment = CrudFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun initAdapter() {
        crudAdapter = CrudAdapter(object : CrudAdapter.OnClickPlanet {
            override fun zoomImage(url: String) {
                showModalImage(url)
            }

            override fun item(product: Product) {
                startActivity(Intent(requireContext(), CrudDetailActivity::class.java).apply {
                    putExtra("planet", product)
                })
            }

        })
        binding.recyclerPlanets.adapter = crudAdapter
    }

    private fun initObservers() {
        viewModel.planetList.observe(viewLifecycleOwner) {
            crudAdapter.submitList(it)
            showShimmerEffect(false)
        }
    }


    fun addPlanet() {
        Snackbar.make(
            binding.root,
            getString(R.string.add_element_under_construction),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showShimmerEffect(showShimmer: Boolean) {
        binding.viewSwitcher.displayedChild = if (showShimmer) {
            binding.shimmerLayout.startShimmer()
            Constants.SHIMMER_LOADER
        } else {
            binding.shimmerLayout.stopShimmer()
            Constants.PLANET_LIST
        }
    }

    private fun showModalImage(url: String) {
        val dialogBinding: DialogImageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(requireContext()),
                R.layout.dialog_image,
                null,
                false
            )

        val dialog = Dialog(requireContext())
        dialogBinding.lifecycleOwner = this

        Glide.with(this).load(url).into(dialogBinding.imageLayout)

        dialogBinding.imageClose.setOnClickListener { dialog.dismiss() }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(Gravity.CENTER)

        dialog.show()
    }
}