package com.aji.suitapps.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aji.suitapps.databinding.UserListBinding
import com.aji.suitapps.utils.responses.DataItem
import com.bumptech.glide.Glide

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var onClick: OnClick
    private var list = ArrayList<DataItem>()

    inner class ViewHolder(val binding: UserListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                Glide.with(itemView.context)
                    .load(avatar)
                    .circleCrop()
                    .into(binding.ivProfile)
                val name = "$firstName $lastName"
                binding.tvUsername.text = name
                binding.tvEmail.text = email
                itemView.setOnClickListener {
                    onClick.onUserClick(name)
                }
            }
        }
    }

    fun list(list: List<DataItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setList(onClick: OnClick) {
        this.onClick = onClick
    }
}