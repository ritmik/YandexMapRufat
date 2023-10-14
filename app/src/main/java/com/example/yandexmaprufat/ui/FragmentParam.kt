package com.example.yandexmaprufat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yandexmaprufat.R
import com.example.yandexmaprufat.databinding.FragmentParamBinding

class FragmentParam : Fragment() {
    private lateinit var viewModel: ParamViewModel
    private var _binding: FragmentParamBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ParamViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      binding.ibSearch.setOnClickListener {
          showPopUp(it)
      }
    }
    fun showPopUp(view: View) {
        val popupMenu = PopupMenu(requireActivity(), view)

        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.services_menu, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.card-> {
                    Toast.makeText(activity, "Test", Toast.LENGTH_LONG).show()
                }


            }
            true
        }
    }


}