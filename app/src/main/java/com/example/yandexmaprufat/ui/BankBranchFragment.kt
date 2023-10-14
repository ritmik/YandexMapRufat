package com.example.yandexmaprufat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yandexmaprufat.R
import com.example.yandexmaprufat.databinding.FragmentBankBranchBinding
import com.example.yandexmaprufat.databinding.FragmentMainBinding
import com.example.yandexmaprufat.ui.banksRV.BankItemTouchHelperCallback
import com.example.yandexmaprufat.ui.banksRV.BankRVAdapter
import com.example.yandexmaprufat.ui.banksRV.VerticalSpaceItemDecoration

class BankBranchFragment : Fragment() {

    private var _binding: FragmentBankBranchBinding? = null
    private val binding get() = _binding!!

    private val projectAdapter = BankRVAdapter()
    private val callback: BankItemTouchHelperCallback =BankItemTouchHelperCallback(projectAdapter)
    private val touchHelper = ItemTouchHelper(callback)

    private lateinit var viewModel: BankBranchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBankBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(binding.rvBank) {
            touchHelper.attachToRecyclerView(this)
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(requireContext())
                .apply {
                    addItemDecoration(
                        VerticalSpaceItemDecoration(50)
                    )
                }
        }

    }

}