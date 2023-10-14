package com.example.yandexmaprufat.ui.banksRV

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yandexmaprufat.data.Bank
import com.example.yandexmaprufat.databinding.ItemBankBinding
import java.util.Collections


class BankRVAdapter() : ListAdapter<Bank, BankViewHolder>(BankDiffCallback()),
    BankItemTouchHelperAdapter {
    var onProjectSwipeListener: ((Bank) -> Unit)?=null
    var onProjectClickListener: ((Bank) -> Unit)? = null
    lateinit var projects: MutableList<Bank>
    lateinit var itemProjectBinding: ItemBankBinding

    fun submit(list:  List<Bank>, rv: RecyclerView) {
        projects=list.toMutableList()
        submitList(projects){
            rv.invalidateItemDecorations()
        }//иначе добавление нового элемента - проблема
    }

    override fun onItemDismiss(position: Int) {
        onProjectSwipeListener?.invoke(projects[position])
        notifyItemRemoved(position)
        projects.removeAt(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(projects, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(projects, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        itemProjectBinding = ItemBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankViewHolder(itemProjectBinding)
    }


    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        with(holder.binding) {
            containerProject.setOnClickListener() {
                onProjectClickListener?.invoke(item)
            }
        }

    }

}