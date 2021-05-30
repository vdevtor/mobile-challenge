package com.example.currentapp.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import com.example.currentapp.R
import com.example.currentapp.databinding.ActivityConvertBinding
import com.example.currentapp.ui.listcurrency.CurrencyListFragment
import com.example.currentapp.utilities.Constants.Api.MAIN_CURRENCY
import com.example.currentapp.utilities.Constants.Api.POSICAO
import com.example.currentapp.utilities.Constants.Api.TO_BE_CONVERTED_CURRENCY
import com.example.currentapp.utilities.currencyIsValid
import com.example.currentapp.utilities.displayToast
import com.example.currentapp.utilities.formatterNumber
import com.example.currentapp.viewmodels.ConvertViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConvertActivity : AppCompatActivity() {
    companion object {
        lateinit var fm: FragmentManager
        private  const val ALL_CURRENCY: String = "All Currencies"
    }

    private var currencyMainList: MutableList<String>? = mutableListOf()
    private var currencyTobeConvertedList: MutableList<String>? = mutableListOf()
    private var livePriceList: Map<String, Double>? = mapOf()
    private var arrayAdapter: ArrayAdapter<String>? = null
    private lateinit var binding: ActivityConvertBinding
    private val viewModel: ConvertViewModel by viewModel()
    private val convertCalc: ConvertCalc by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConvertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Chamando viewModel -> Api
        fm = supportFragmentManager
        viewModel.getList()
        viewModel.getPrices()
        setObservables()

        //Observando Spinners
        onMainSpinnerSelectedListener()
        onTobeConvertedSpinnerSelectedListener()

        //Observando btn convert
        onBtnConvertClickListener()

        binding.btnShowList.setOnClickListener {
            inflateFragment()
        }
    }

    private fun inflateFragment() {
        fm.beginTransaction().add(binding.fragmentContainer.id, CurrencyListFragment())
                .addToBackStack(null).commit()
    }

    private fun onBtnConvertClickListener() {
        binding.btnConvert.setOnClickListener {
            convert()
        }
    }

    private fun onMainSpinnerSelectedListener() {
        binding.mainCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val s = binding.mainCurrency.getItemAtPosition(position).toString()
                MAIN_CURRENCY = s
                POSICAO = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                MAIN_CURRENCY = ""
            }
        }
    }

    private fun onTobeConvertedSpinnerSelectedListener() {
        binding.currencyToBeConverted.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val s = binding.currencyToBeConverted.getItemAtPosition(position).toString()
                TO_BE_CONVERTED_CURRENCY = s
                POSICAO = position

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TO_BE_CONVERTED_CURRENCY = ""
            }

        }
    }

    private fun setObservables() {
        getCurrencyList()
        getLivePrice()
    }

    private fun getLivePrice() {
        viewModel.onResultLivePrices.observe(this, { livePrices ->
            livePriceList = livePrices.livePrice
        })
    }

    private fun getCurrencyList() {
        viewModel.onResultListOfCurrency.observe(this, { currencyList ->
            currencyList.currencyList?.forEach {
                currencyMainList?.add(it.key)
                currencyTobeConvertedList?.add(it.key)
            }
        })
        populateSpinners()
    }

    private fun populateSpinners() {
        currencyMainList?.add(0, ALL_CURRENCY)

        currencyTobeConvertedList?.add(0, ALL_CURRENCY)

        binding.mainCurrency.adapter = updateSpinner(currencyMainList as ArrayList<String>?)

        binding.currencyToBeConverted.adapter = updateSpinner(currencyTobeConvertedList as ArrayList<String>?)

    }

    private fun updateSpinner(listOfCurrency: ArrayList<String>?): ArrayAdapter<String>? {

        arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
                listOfCurrency ?: emptyList())
        return arrayAdapter

    }

    private fun convert() {
        if (currencyIsValid(binding.mainCurrency, ALL_CURRENCY)) {
            displayToast("Selecione a Moeda a ser convertida")
        }
        val mainCurrency = convertCalc.currencyToDollar(livePriceList)

        val tobeConvertedCurrency = convertCalc.tobeConvertedCurrencyValue(livePriceList)

        if (!binding.valueToBeConverted.text.isNullOrEmpty()) {
            val desireValue = convertCalc.desireValue(binding.valueToBeConverted)

            val result = (mainCurrency?.times(tobeConvertedCurrency))?.times(desireValue)

            binding.convertedValue.text = result?.let { formatterNumber(it) }

        } else {
            val result = mainCurrency?.times(tobeConvertedCurrency)
            binding.convertedValue.text = result?.let { formatterNumber(result) }
        }
    }
}