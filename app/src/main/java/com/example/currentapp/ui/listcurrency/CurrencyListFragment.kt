package com.example.currentapp.ui.listcurrency

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currentapp.R
import com.example.currentapp.databinding.FragmentCurrencyListBinding
import com.example.currentapp.ui.convert.ConvertFragment
import com.example.currentapp.ui.mainactivity.ConvertActivity.Companion.fm
import com.example.currentapp.ui.convert.ConvertViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CurrencyListFragment : Fragment() {

    private var currencyListBinding: FragmentCurrencyListBinding? = null
    private val currentListViewModel: ConvertViewModel by sharedViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        currencyListBinding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return currencyListBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentListViewModel.getList()
        observerList()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            returnToMainFragment()
        }
    }

    private fun returnToMainFragment() {

        fm.beginTransaction().replace(R.id.fragment_container, ConvertFragment.newInstance())
                .commit()
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

    companion object {
        fun newInstance(): Fragment {
            return CurrencyListFragment()
        }
    }

    private fun OnBackPressedDispatcher.addCallback(viewLifecycleOwner: LifecycleOwner, function: () -> Unit) {

    }
}


