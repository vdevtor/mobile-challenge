package com.example.currentapp.ui.listcurrency

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currentapp.databinding.FragmentCurrencyListBinding
import com.example.currentapp.viewmodels.ConvertViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CurrencyListFragment : Fragment() {

    private var currencyListBinding: FragmentCurrencyListBinding? = null
    private val currentListViewModel by viewModel<ConvertViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        currencyListBinding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return currencyListBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentListViewModel.getList()
        observerList()
    }

    private fun observerList() {
        currentListViewModel.onResultListOfCurrency.observe(this.viewLifecycleOwner, {
            val values = it.currencyList?.values
            val codes = it.currencyList?.keys
            val adapter = CurrencyListAdapter(values, codes)
            setupRecyclerView(adapter)
        })
    }

    private fun setupRecyclerView(adapterCurerncy: CurrencyListAdapter) {
        currencyListBinding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = adapterCurerncy
        }
    }
}