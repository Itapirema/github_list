package br.com.challenge.github.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.databinding.LayoutUserListItemBinding
import com.bumptech.glide.Glide

class UserListAdapter(
    private val itemClickListener: ItemClickListener? = null
): ListAdapter<UserDTO, UserListAdapter.UserViewHolder>(UserDiffCallback) {

    interface ItemClickListener {
        fun onItemClick(user: UserDTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(LayoutUserListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class UserViewHolder(private val binding: LayoutUserListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDTO, itemClickListener: ItemClickListener?) {
            binding.apply {
                txtUserName.text = user.login
                user.avatarUrl?.let {
                    Glide.with(root.context).load(it).centerCrop().into(imgUser)
                }
                root.setOnClickListener{
                    itemClickListener?.onItemClick(user)
                }
            }
        }
    }

    object UserDiffCallback: DiffUtil.ItemCallback<UserDTO>() {
        override fun areItemsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
            return oldItem.id == newItem.id
        }
    }
}