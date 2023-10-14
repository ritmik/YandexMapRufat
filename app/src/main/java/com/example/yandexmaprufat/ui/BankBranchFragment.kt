package com.example.yandexmaprufat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yandexmaprufat.App
import com.example.yandexmaprufat.R
import com.example.yandexmaprufat.databinding.FragmentBankBranchBinding
import com.example.yandexmaprufat.databinding.FragmentMainBinding
import com.example.yandexmaprufat.ui.banksRV.BankItemTouchHelperCallback
import com.example.yandexmaprufat.ui.banksRV.BankRVAdapter
import com.example.yandexmaprufat.ui.banksRV.VerticalSpaceItemDecoration
import com.example.yandexmaprufat.ui.vmfactories.BankBranchViewModelFactory
import com.example.yandexmaprufat.ui.vmfactories.MainViewModelFactory
import kotlinx.coroutines.launch

class BankBranchFragment : Fragment() {

    private var _binding: FragmentBankBranchBinding? = null
    private val binding get() = _binding!!

    private val projectAdapter = BankRVAdapter()
    private val callback: BankItemTouchHelperCallback =BankItemTouchHelperCallback(projectAdapter)
    private val touchHelper = ItemTouchHelper(callback)
    val viewModel: BankBranchViewModel by viewModels { BankBranchViewModelFactory((requireActivity().application as App).mainRepository) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBankBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bankList.flowWithLifecycle(
                viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED
            ).collect {
                projectAdapter.submit(it,binding.rvBank)
            }
        }


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